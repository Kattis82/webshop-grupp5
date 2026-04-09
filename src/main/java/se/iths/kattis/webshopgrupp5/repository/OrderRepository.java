package se.iths.kattis.webshopgrupp5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.kattis.webshopgrupp5.model.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // kan användas till orderhistorik på profilsidan om vi skulle vilja ha det
    List<Order> findByUsername(String username);
}
