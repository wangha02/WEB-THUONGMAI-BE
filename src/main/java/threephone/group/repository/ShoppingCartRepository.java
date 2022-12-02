package threephone.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import threephone.group.model.cart.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart , Long> {
}
