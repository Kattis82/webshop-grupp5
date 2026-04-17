package se.iths.kattis.webshopgrupp5.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import se.iths.kattis.webshopgrupp5.model.Product;
import se.iths.kattis.webshopgrupp5.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class ProductServiceH2Test {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    Product product;
    List<Product> products = new ArrayList<>();

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();

        product = new Product();
        product.setName("Margharita");
        product.setPrice(100.0);
        product.setCategory("Pizza");
        product.setPictureUrl(null);
        productRepository.save(product);
    }

    @Test
    @DisplayName("Testar så alla produkter hämtas")
    void getAllProducts() {
        products = productService.getAllProducts();
        Assertions.assertEquals(1, products.size());
        Assertions.assertEquals("Margharita", products.get(0).getName());
    }

    @Test
    @DisplayName("Testar så produkt hämtas under rätt kategori")
    void findByCategory() {
        products.add(product);
        List<Product> result = productService.findByCategory("Pizza");

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Margharita", products.get(0).getName());
    }

    @Test
    @DisplayName("Testar så rätt produkt hämtas under rätt namn")
    void findByProductname() {
        products.add(product);
        Product result = productService.findByProductname("Margharita");

        Assertions.assertEquals("Margharita", products.get(0).getName());
    }

    @Test
    @DisplayName("Testar så produkt hittas när man söker")
    void search() {
        products.add(product);
        List<Product> result = productService.search("Mar");

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Margharita", products.get(0).getName());
    }

    @Test
    @DisplayName("Testar så rätt produkt hämta sunder id")
    void findById() {
        Product savedProduct = productRepository.findAll().get(0);
        Product result = productService.findById(savedProduct.getId());

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Margharita", result.getName());
        Assertions.assertEquals("Pizza", result.getCategory());
        Assertions.assertEquals(100.0, result.getPrice());
    }

    @Test
    @DisplayName("Testar så produkt skapas")
    void create() {
        Product save = productService.create("Risotto", 120.0, "Pasta", null);

        Assertions.assertNotNull(save.getId());
    }

    @Test
    @DisplayName("Testar så produkt raderas")
    void deleteProductById() {
        productService.deleteProductById(1L);
        Assertions.assertEquals(0, productRepository.count());
    }
}