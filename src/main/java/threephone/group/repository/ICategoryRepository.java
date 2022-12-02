package threephone.group.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import threephone.group.model.category.Category;
import threephone.group.model.product.Product;

import java.util.List;

public interface ICategoryRepository extends JpaRepository<Category,Long> {

    List<Category> findByName(String name);
    Page<Category> findByNameContaining(String name, Pageable pageable);
    Boolean existsByName(String name);
}
