package se.iths.kattis.webshopgrupp5.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import se.iths.kattis.webshopgrupp5.model.Product;
import se.iths.kattis.webshopgrupp5.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceMockitoTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    Product product;
    List<Product> products = new ArrayList<>();

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("Carbonara");
        product.setPrice(130.0);
        product.setCategory("Pasta");
        product.setPictureUrl(null);
    }

    @Test
    @DisplayName("Kollar så alla produkter hämtas")
    void getAllProductsTest() {
        products.add(product);
        Mockito.when(productRepository.findAll()).thenReturn(products);
        List<Product> result = productService.getAllProducts();
        assertEquals(products, result);
    }

    @Test
    @DisplayName("Kollar så produkt hämtas under kategori")
    void findByCategoryTest() {
        products.add(product);
        Mockito.when(productRepository.findByCategory("Pasta")).thenReturn(products);
        List<Product> result = productService.findByCategory(product.getCategory());
        assertEquals(products, result);
    }

    @Test
    @DisplayName("Letar produkt efter namn")
    void findByProductnameTest() {
        Mockito.when(productRepository.findByName("Carbonara")).thenReturn(Optional.of(product));
        Product result = productService.findByProductname(product.getName());
        assertEquals(product, result);
    }

    @Test
    @DisplayName("Letar produkt med liknande namn")
    void searchTest() {
        products.add(product);
        Mockito.when(productRepository.findByNameContainingIgnoreCase(Mockito.anyString()))
                .thenReturn(products);
        List<Product> result = productService.search("Car");
        assertEquals(products, result);
    }

    @Test
    @DisplayName("Letar produkt efter ID")
    void findByIdTest() {
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Product result = productService.findById(product.getId());
        assertEquals(product, result);
    }

    @Test
    @DisplayName("Kollar så produkt skapas korrekt")
    void createTest() {
        Mockito.when(productRepository.save(Mockito.any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        Product product1 = productService.create("Test", 10.0, "Kategori", null);
        verify(productRepository).save(Mockito.any(Product.class));
    }

    @Test
    @DisplayName("Kollar så produkt raderas")
    void deleteProductByIdTest() {
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        productService.deleteProductById(1L);
        verify(productRepository).findById(1L);
        verify(productRepository).delete(product);
    }
}