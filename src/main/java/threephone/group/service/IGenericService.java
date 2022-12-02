package threephone.group.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import threephone.group.model.product.Product;

import java.util.List;
import java.util.Optional;

public interface IGenericService<T> {
    List<T> findAll();
    Page<T> findAll(Pageable pageable);
    Optional<T> findById(Long id);
    T save (T t);
    void remote(Long id);
    List<T> findByName(String name);
    Boolean existsByName(String name);
    Page<T> findByNameContaining(String name, Pageable pageable);

}
