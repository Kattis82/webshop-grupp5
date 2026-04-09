package se.iths.kattis.webshopgrupp5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.kattis.webshopgrupp5.model.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProductId(Long productId);

    Long productId(Long productId);

    List<Product> findByCategory(String category);
}
