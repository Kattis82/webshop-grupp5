package se.iths.kattis.webshopgrupp5.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.iths.kattis.webshopgrupp5.model.Product;

@ExtendWith(MockitoExtension.class)
class ProductServiceMockitoTest {

    @Mock
    Product product;

    @InjectMocks
    ProductService productService;


    @Test
    void getAllProducts() {
    }

    @Test
    void findByCategory() {
    }

    @Test
    void findByProductname() {
    }

    @Test
    void search() {
    }

    @Test
    void findById() {
    }

    @Test
    void create() {
    }

    @Test
    void deleteProductByName() {
    }
}