package se.iths.kattis.webshopgrupp5.service;

import org.springframework.stereotype.Service;
import se.iths.kattis.webshopgrupp5.model.Product;
import se.iths.kattis.webshopgrupp5.model.cart.Cart;
import se.iths.kattis.webshopgrupp5.model.cart.CartItem;

import java.util.List;

@Service
public class CartService {

    private final Cart cart;

    public CartService(Cart cart) {
        this.cart = cart;
    }

    // metod som lägger till produkt i kundvagnen
    public void addProduct(Product product) {
        cart.addProduct(product);
    }


    // metod hämta allt innehåll (alla rader) i kundvagnen
    public List<CartItem> getCartItems() {
        return cart.getCartItems();
    }


    // metod hämta totalpriset för kundvagnen
    public double getTotalPrice() {
        return cart.getTotalPrice();
    }

    // metod för att tömma kundvagnen
    public void clearCart() {
        cart.clearCart();
    }
}
