package threephone.group.service.order;

import threephone.group.model.User;
import threephone.group.model.cart.Orders;

import java.util.List;
import java.util.Optional;

public interface IOderService {
    List<Orders> findAll();
    void save(Orders order);
    void deleteById(Long id);
    Optional<Orders> findById(Long id);
    List<Orders> findByUser(User user);
}
