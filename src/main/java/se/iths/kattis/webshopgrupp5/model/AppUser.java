package se.iths.kattis.webshopgrupp5.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username; // email
    private String password;
    private boolean consent;
    private String role;

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();


    public AppUser() {
    }

    public AppUser(String username, String password, boolean consent, String role) {
        this.username = username;
        this.password = password;
        this.consent = consent;
        this.role = role;
    }

}
