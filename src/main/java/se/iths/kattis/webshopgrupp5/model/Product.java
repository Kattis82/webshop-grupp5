package se.iths.kattis.webshopgrupp5.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;
    private String category;
    private String pictureUrl;


    public Product() {
    }

    public Product(String name, Double price, String category, String pictureUrl) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.pictureUrl = pictureUrl;
    }
}
