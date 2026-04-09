package se.iths.kattis.webshopgrupp5.service;

import org.springframework.stereotype.Service;
import se.iths.kattis.webshopgrupp5.exception.ProductNotFoundException;
import se.iths.kattis.webshopgrupp5.model.AppUser;
import se.iths.kattis.webshopgrupp5.model.Product;
import se.iths.kattis.webshopgrupp5.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;
    private Product product;
    private AppUser appUser;

    public ProductService() {
    }

    public ProductService(Product product, ProductRepository productRepository, AppUser appUser) {
        this.product = product;
        this.productRepository = productRepository;
        this.appUser = appUser;
    }

    //visa alla produkter
    public List<Product> showAllProducts() {
        List<Product> products = productRepository.findAll();
        return products;
    }

    //sortera per kategori
    public List<Product> sortByCategory(String category) {
        List<Product> categorys = productRepository.findByCategory(category);
        return categorys;
    }

    //skapa ny product om admin
    public Product createProduct(String name, Double price, String category, String pictureUrl) {
        if (appUser.getRole().equals("ADMIN")) {
            Product product = new Product(name, price, category, pictureUrl);
            productRepository.save(product);
            return product;
        }
        return null;
    }

    //Hitta produkt via namn
    public List<Product> findByProductname(String name) {
        try {
            List<Product> product = productRepository.findByName(name);
            return product;
        } catch (ProductNotFoundException e) {
            System.out.println("Product not found");
            return null;
        }
    }
}
