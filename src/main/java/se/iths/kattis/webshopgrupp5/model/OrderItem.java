package se.iths.kattis.webshopgrupp5.model;

import lombok.Getter;
import lombok.Setter;

//@Entity när relationerna skapas används @Entity
@Getter
@Setter
public class OrderItem {

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private Integer number;
    private Double price;

    public OrderItem() {
    }

    public OrderItem(String productName, Integer number, Double price) {
        this.productName = productName;
        this.number = number;
        this.price = price;
    }
}
