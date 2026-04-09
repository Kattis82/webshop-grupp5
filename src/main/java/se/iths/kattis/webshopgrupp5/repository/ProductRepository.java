package se.iths.kattis.webshopgrupp5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.iths.kattis.webshopgrupp5.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProductId(Long id);

    Long productId(Long id);

    List<Product> findByCategory(String category);
}