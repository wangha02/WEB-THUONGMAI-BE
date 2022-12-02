package threephone.group.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import threephone.group.dto.response.ResponseMessage;
import threephone.group.model.category.Category;
import threephone.group.model.product.Product;
import threephone.group.service.category.CategoryServiceIMPL;
import threephone.group.service.product.IProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {
    @Autowired
    private IProductService productService;
    @Autowired
    private CategoryServiceIMPL categoryServiceIMPL;
    @GetMapping
    public ResponseEntity<?> showListProduct(Pageable pageable){
        Page<Product> products = productService.findAll(pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody Product product){
        productService.save(product);
        return new ResponseEntity<>(new ResponseMessage("Create success!"), HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<?> pageProduct(@PageableDefault(sort = "name", size = 9) Pageable pageable){
        return new ResponseEntity<>(productService.findAll(pageable),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detailProduct(@PathVariable Long id){
        if (!productService.findById(id).isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productService.findById(id).get(),HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id ,@RequestBody Product product){
        Optional<Product> product1 = productService.findById(id);
        if (!product1.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (product.getName().trim().equals("")){
            return new ResponseEntity<>(new ResponseMessage("The name product invalid"),HttpStatus.OK);
        }
        if (productService.existsByName(product.getName())){
            return new ResponseEntity<>(new ResponseMessage("The name product exited !"),HttpStatus.OK);
        }
        product1.get().setName(product.getName());
        product1.get().setDescription(product.getDescription());
        product1.get().setManufacture(product.getManufacture());
        product1.get().setPrice(product.getPrice());
        productService.save(product1.get());
        return new ResponseEntity<>(new ResponseMessage("Update success !"),HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        Optional<Product> product = productService.findById(id);
        if(!product.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productService.remote(id);
        return new ResponseEntity<>(new ResponseMessage("Delete success !"),HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<?> searchByNamePage(@RequestParam String name,Pageable pageable){
        if (name.trim().equals("")){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productService.findByNameContaining(name,pageable),HttpStatus.OK);
    }
    @GetMapping("/theme")
    public ResponseEntity<?> themeById(){
        List<Product> product = productService.findAll();
        List<Product> products = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < product.size() ; i++) {
            if (product.get(i).getQuantity()> 100 ){
                count ++;
                products.add(product.get(i));
                if (count == 10){
                    break;
                }
            }
        }
        return new ResponseEntity<>(products,HttpStatus.OK);
    }
}


