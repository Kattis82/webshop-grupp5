package se.iths.kattis.webshopgrupp5.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // listan används när vi skapar relationerna mellan tabeller
    //private List<OrderItem> orderItems = new ArrayList<>();

    private String username;   // användaren som har gjort ordern
    private Double totalPrice;
    private LocalDate orderDate;

    public Order() {
    }

    public Order(String username, Double totalPrice, LocalDate orderDate) {
        this.username = username;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
    }


    // Hjälpmetod för att lägga till OrderItem
    // används senare i service-lagret när Order skapas från kundvagnen
//    public void addOrderItem(OrderItem item) {
//        this.orderItems.add(item);
//    }


}
