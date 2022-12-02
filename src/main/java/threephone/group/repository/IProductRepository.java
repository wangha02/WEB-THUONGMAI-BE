package threephone.group.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import threephone.group.model.product.Product;
import java.util.List;

public interface IProductRepository extends JpaRepository<Product , Long> {
    @Query(value = "SELECT * FROM products pr WHERE pr.category_id= ?1",nativeQuery = true)
    List<Product> findStudentIdCategory(Long id);
    List<Product> findByName(String name);
    Page<Product> findByNameContaining(String name, Pageable pageable);
    Boolean existsByName(String name);
}
