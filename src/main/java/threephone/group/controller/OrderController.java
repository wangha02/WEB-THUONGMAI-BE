package threephone.group.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import threephone.group.dto.response.ResponseMessage;
import threephone.group.dto.shoppingCart.OrderResponseDTO;
import threephone.group.model.User;
import threephone.group.model.cart.Orders;
import threephone.group.model.product.Product;
import threephone.group.security.userprincipal.UserDetailService;
import threephone.group.service.order.OrderServiceIMPL;
import threephone.group.service.product.ProductService;

import java.util.*;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/oder")
public class OrderController {
    @Autowired
    private OrderServiceIMPL orderServiceIMPL;
    @Autowired
    UserDetailService userDetailService;
    @Autowired
    ProductService productService;
    @GetMapping
    public ResponseEntity<?> showListOrder(){
        return new ResponseEntity<>(orderServiceIMPL.findAll(), HttpStatus.OK);
    }
    @PostMapping("/addItems")
    public ResponseEntity<?> createOrder(@RequestBody Orders order){
        User user = userDetailService.getCurrentUser();
        if(user.getUsername().equals("Anonymous")){
            return new ResponseEntity<>(new ResponseMessage("Not_Found"), HttpStatus.OK);
        }
        orderServiceIMPL.save(order);
        return new ResponseEntity<>(new ResponseMessage("create_success"), HttpStatus.OK);
    }
    @GetMapping("/shoppingCart")
    public ResponseEntity<?> findOrderByUser(){
        User user = userDetailService.getCurrentUser();
        List<Orders> orderList = orderServiceIMPL.findByUser(user);
        List<Long> products = new ArrayList<>();
        for (int i = 0; i < orderList.size(); i++) {
            products.add(orderList.get(i).getProducts().get(0).getId());
        }
        Set<Long> setIdProduct = new HashSet<>();
        for (int i = 0; i < products.size(); i++) {
            setIdProduct.add(products.get(i));
        }
        OrderResponseDTO responseDTO = new OrderResponseDTO();
        List<Product> products1 = new ArrayList<>();
        //List id cua Product khong trung lap
        List<Long> countList = new ArrayList<>(setIdProduct);
        for (int i = 0; i < countList.size(); i++) {
            Optional<Product> product = productService.findById(countList.get(i));
            products1.add(product.get());
        }
        responseDTO.setProducts(products1);

        //List so lần xuất hiện product tương đương với list ID ở trên

        List<Integer> countProduct = new ArrayList<>();
        for (int i = 0; i < countList.size(); i++) {
            int count = Collections.frequency(products, countList.get(i));
            countProduct.add(count);
        }

        responseDTO.setAmount(countProduct);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
