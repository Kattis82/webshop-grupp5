package se.iths.kattis.webshopgrupp5.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.iths.kattis.webshopgrupp5.model.Product;
import se.iths.kattis.webshopgrupp5.model.cart.Cart;
import se.iths.kattis.webshopgrupp5.model.cart.CartItem;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// använder ingen databas eller repository, i denna testklass
// testas själva klassen = enhetstest
// CartService testas isolerat med mockade beroenden

@ExtendWith(MockitoExtension.class)
public class CartServiceMockTest {

    // låtsas kundvagn
    @Mock
    Cart cart;

    // riktig CartService injiceras
    @InjectMocks
    CartService cartService;

    @Test
    @DisplayName("Ska testa att produkt läggs till i kundvagnen")
    void testAddProduct() {
        // arrange
        Product product = new Product("testprodukt", 50.0,
                "testkategori", null); // null = ingen bild

        // act - kör metoden
        cartService.addProduct(product);

        // assert - kollar så att metoden i cart anropas (void-metod)
        verify(cart).addProduct(product);
    }

    @Test
    @DisplayName("Ska testa att innehåll i kundvagnen returneras")
    void testGetCartItems() {
        // arrange - förbered ett innehåll/en rad i kundvagnen
        List<CartItem> cartItems = List.of(new CartItem(1L,
                "testprodukt", 50.0, 3));

        // när metoden anropas...returnera cartItems-listan istället
        when(cart.getCartItems()).thenReturn(cartItems);

        // act - kör metoden i CartService
        List<CartItem> result = cartService.getCartItems();

        // assert - kollar så att det är ett cartItem i result
        Assertions.assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Ska testa att rätt totalpris returneras")
    void testGetTotalPrice() {
        // arrange - när metoden anropas...returnera totalpriset 150 istället
        when(cart.getTotalPrice()).thenReturn(150.0);

        // act - kör metoden i CartService
        double result = cartService.getTotalPrice();

        // assert - kollar så resultatet som returneras från metoden stämmer
        Assertions.assertEquals(150, result);

    }


    @Test
    @DisplayName("Ska testa att kundvagnen töms")
    void testClearCart() {
        // act - kör metoden i CartService
        cartService.clearCart();

        // assert - kollar så att metoden i cart anropas (void-metod)
        verify(cart).clearCart();

    }


}
