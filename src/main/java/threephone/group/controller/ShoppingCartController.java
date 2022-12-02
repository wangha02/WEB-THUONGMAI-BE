package threephone.group.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import threephone.group.model.cart.Orders;
import threephone.group.repository.ShoppingCartRepository;
import threephone.group.service.shoppingCart.ShoppingCartServiceIMPL;
import threephone.group.service.user.UserServiceIMPL;

@RestController
@RequestMapping("/api/shoppingCart")
@CrossOrigin
public class ShoppingCartController {
    @Autowired
    private ShoppingCartServiceIMPL orderServiceIMPL;
    @Autowired
    private UserServiceIMPL userServiceIMPL;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;


    private Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);

    @GetMapping(value = "/order/{orderId}")
    public ResponseEntity<?> getOrderDetail(@PathVariable Long orderId){
        Orders order = orderServiceIMPL.getOrderDetail(orderId);
        return ResponseEntity.ok(order);
    }
//    @PostMapping("/placeOrder")
//    public ResponseEntity<?> placeOrder(@RequestBody OrderDTO orderDTO){
//        logger.info("Request Payload" + orderDTO.toString());
//        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
//        int amount = orderServiceIMPL.getCartAmount(orderDTO.getCartItems());
//        User user = new User(orderDTO.getUsername(),orderDTO.getUserEmail());
//        Long userID= userServiceIMPL.isUserPresent(user);
//        if (userID!= null){
//            user.setId(userID);
//            logger.info("User already present in db with id: " + userID);
//        }else{
//            user = userServiceIMPL.save(user);
//            logger.info("User saved.. with name : " + user.getName());
//        }
//        Order order = new Order(orderDTO.getOrderDescription(),user,orderDTO.getCartItems());
//        order = orderServiceIMPL.save(order);
//        logger.info("Oder processed successfully..");
//        orderResponseDTO.setAmount(amount);
//        orderResponseDTO.setDate(DateUtil.getCurrentDateTime());
//        orderResponseDTO.setInvoiceNumber(new Random().nextInt(10000));
//        orderResponseDTO.setOrderId(order.getId());
//        orderResponseDTO.setOrderDescription(orderDTO.getOrderDescription());
//        return ResponseEntity.ok(orderResponseDTO);
//    }
    @GetMapping("/show")
    public ResponseEntity<?> showShoppingCart(){
        shoppingCartRepository.findAll();
        return ResponseEntity.ok(shoppingCartRepository.findAll());
    }

}
