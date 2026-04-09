package se.iths.kattis.webshopgrupp5.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String username;   // användaren som har gjort ordern
    private Double totalPrice;
    private LocalDate orderDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order() {
    }

    public Order(String username, Double totalPrice, LocalDate orderDate) {
        this.username = username;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
    }


    // Hjälpmetod för att lägga till OrderItem
    // används senare i service-lagret när Order skapas från kundvagnen
    public void addOrderItem(OrderItem item) {
        item.setOrder(this);
        this.orderItems.add(item);
    }


}
