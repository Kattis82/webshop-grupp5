package se.iths.kattis.webshopgrupp5.model.cart;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import se.iths.kattis.webshopgrupp5.model.Product;

import java.util.ArrayList;
import java.util.List;

@Component
@SessionScope
public class Cart {

    // själva kundvagnen - innehåller alla produkter användaren lagt till
    private List<CartItem> cartItems = new ArrayList<>();

    // metod som lägger till produkt i kundvagn eller ökar antal om produkten
    // redan finns
    public void addProduct(Product product) {

        // gå igenom varje rad i kundvagnen
        for (CartItem cartItem : cartItems) {

            // om cartItem innehåller samma productId som produktens id
            // ökas antalet med 1
            // productId sparas i CartItem när objektet skapas via konstruktorn
            if (cartItem.getProductId().equals(product.getId())) {
                cartItem.increaseQuantity();
                return;
            }
        }

        // när raden skapas hämtas produktens id, namn och pris
        cartItems.add(new CartItem(
                product.getId(),
                product.getName(),
                product.getPrice(),
                1
        ));

    }

    // lista alla produkter
    public List<CartItem> getCartItems() {
        return cartItems;
    }

    // räkna ut totalpris
    public double getTotalPrice() {
        // gå igenom cartItems-listan
        return cartItems.stream()
                // för varje CartItem, hämta dess totalpris
                .mapToDouble(cartItem -> cartItem.getTotalPrice())
                // addera alla värden
                .sum();
    }

    // töm kundvagnen
    public void clearCart() {
        cartItems.clear();
    }


}
