package se.iths.kattis.webshopgrupp5.model.cart;

import lombok.Getter;
import lombok.Setter;

// en rad i kundvagnen
@Getter
@Setter
public class CartItem {

    private Long productId;
    private String productName;
    private double productPrice;
    private int quantity;

    public CartItem(Long productId, String productName, double productPrice, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    // metod som ökar antal om produkten finns
    public void increaseQuantity() {
        this.quantity++;
    }

    // metod för att räkna ut totalpris
    public double getTotalPrice() {
        return productPrice * quantity;
    }
}
