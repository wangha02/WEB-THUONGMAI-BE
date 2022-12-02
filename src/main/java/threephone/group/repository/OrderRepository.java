package threephone.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import threephone.group.model.User;
import threephone.group.model.cart.Orders;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {

    List<Orders> findByUser(User user);
}
