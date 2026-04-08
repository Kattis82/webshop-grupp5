package se.iths.kattis.webshopgrupp5.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems = new ArrayList<>();

    public Product() {
    }

    public Product(String name, Double price, String category, String pictureUrl) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.pictureUrl = pictureUrl;
    }
}
