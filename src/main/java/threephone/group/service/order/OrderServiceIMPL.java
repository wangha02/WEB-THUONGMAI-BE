package threephone.group.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import threephone.group.model.User;
import threephone.group.model.cart.Orders;
import threephone.group.repository.OrderRepository;
import threephone.group.security.userprincipal.UserDetailService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Service
@Transactional

public class OrderServiceIMPL implements IOderService{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserDetailService userDetailService;
    @Override
    public List<Orders> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public void save(Orders order) {
        User user = userDetailService.getCurrentUser();
        order.setUser(user);
        orderRepository.save(order);
    }

    @Override
    public void deleteById(Long id) {
orderRepository.deleteById(id);
    }

    @Override
    public Optional<Orders> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Orders> findByUser(User user) {
        return orderRepository.findByUser(user);
    }
}
