package se.iths.kattis.webshopgrupp5.service;

import org.springframework.stereotype.Service;
import se.iths.kattis.webshopgrupp5.exception.ProductNotFoundException;
import se.iths.kattis.webshopgrupp5.model.Product;
import se.iths.kattis.webshopgrupp5.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //visa alla produkter
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    //sortera per kategori
    public List<Product> findByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    //Hitta produkt via namn
    public List<Product> findByProductname(String name) {
        return productRepository.findByName(name);
    }

    //Hitta produkt via id
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }
}
