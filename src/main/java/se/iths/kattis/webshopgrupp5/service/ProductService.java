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
    public Product findByProductname(String name) {
        return productRepository.findByName(name)
                .orElseThrow(() -> new ProductNotFoundException("Produkten hittades inte"));
    }

    //Söka efter produkt med söknamnet eller liknande produkter
    public List<Product> search(String query) {
        return productRepository.findByNameContainingIgnoreCase(query);
    }

    //Hitta produkt via id
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produkten hittades inte"));
    }

    //Skapa produkt
    public Product create(String name, Double price, String category, String pictureUrl) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setCategory(category);
        product.setPictureUrl(pictureUrl);

        return productRepository.save(product);
    }

    public void deleteProductByName(String name) {
        Product product = productRepository.findByName(name)
                .orElseThrow(() -> new ProductNotFoundException("Produkten hittades inte"));
        productRepository.delete(product);
    }
}
