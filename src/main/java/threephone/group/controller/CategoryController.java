package threephone.group.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import threephone.group.dto.response.CategoryResponse;
import threephone.group.dto.response.ResponseMessage;
import threephone.group.model.category.Category;
import threephone.group.repository.ICategoryRepository;
import threephone.group.repository.IProductRepository;
import threephone.group.service.category.ICategoryService;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/categories")
@CrossOrigin
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private ICategoryRepository categoryRepository;
    @Autowired
    private IProductRepository productRepository;
    @GetMapping("/pageCategory")
    public ResponseEntity<?> showListCategory(Pageable pageable){
        Page<Category> categories = categoryService.findAll(pageable);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<?> showListCategory(){
        List<Category> categories = categoryService.findAll();
        return new ResponseEntity<>(categories,HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity createProduct(@RequestBody Category category){
        if (category.getName().trim().equals("")) {
            return new ResponseEntity<>(new ResponseMessage("The name product invalid"), HttpStatus.OK);
        }
        if (categoryService.existsByName(category.getName())) {
            return new ResponseEntity<>(new ResponseMessage("The name product exited"), HttpStatus.OK);
        }
        categoryService.save(category);
        return new ResponseEntity<>(new ResponseMessage("Create success!"), HttpStatus.OK);

    }
    @GetMapping("/page")
    public ResponseEntity<?> pageCategory(@PageableDefault(sort = "name",size = 5) Pageable pageable){
        return new ResponseEntity<>(categoryService.findAll(pageable),HttpStatus.OK);
    }
    @PutMapping("{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id ,@RequestBody Category category){
        Optional<Category> category1 = categoryService.findById(id);
        if (!category1.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (category.getName().trim().equals("")){
            return new ResponseEntity<>(new ResponseMessage("The name category invalid"),HttpStatus.OK);
        }
        if (categoryService.existsByName(category.getName())){
            return new ResponseEntity<>(new ResponseMessage("The name category exited !"),HttpStatus.OK);
        }
        category1.get().setName(category.getName());
        category1.get().setAvatar(category.getAvatar());
        categoryService.save(category1.get());
        return new ResponseEntity<>(new ResponseMessage("Update success !"),HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){
        Optional<Category> category = categoryService.findById(id);
        if(!category.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        categoryService.remote(id);
        return new ResponseEntity<>(new ResponseMessage("Delete success !"),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id){
        CategoryResponse categoryResponse = new CategoryResponse();
        Category category = categoryService.findById(id).get();
        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());
        categoryResponse.setAvatar(category.getAvatar());
        categoryResponse.setProducts(productRepository.findStudentIdCategory(id));
        return new ResponseEntity<>(categoryResponse,HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchByNamePage(@RequestParam String name,Pageable pageable){
        if (name.trim().equals("")){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categoryService.findByNameContaining(name,pageable),HttpStatus.OK);
    }
}
