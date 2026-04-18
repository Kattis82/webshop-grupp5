package se.iths.kattis.webshopgrupp5.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import se.iths.kattis.webshopgrupp5.exception.ProductNotFoundException;
import se.iths.kattis.webshopgrupp5.model.Product;
import se.iths.kattis.webshopgrupp5.repository.ProductRepository;
import se.iths.kattis.webshopgrupp5.service.CartService;
import se.iths.kattis.webshopgrupp5.service.OrderService;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final ProductRepository productRepository;
    private final CartService cartService;
    private final OrderService orderService;

    public CartController(ProductRepository productRepository, CartService cartService, OrderService orderService) {
        this.productRepository = productRepository;
        this.cartService = cartService;
        this.orderService = orderService;
    }

    // visa innehållet (varorna) i kundvagnen inkl totalpriset
    @GetMapping
    public String showCart(Model model) {
        model.addAttribute("cartItems", cartService.getCartItems());
        model.addAttribute("totalPrice", cartService.getTotalPrice());
        return "cart";
    }


    // lägg till produkt i kundvagnen mha produktid,om produkten finns
    @PostMapping("/add/{productId}")
    public String addToCart(@PathVariable Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Produkten hittades inte"));
        cartService.addProduct(product);
        return "redirect:/cart";
    }


    // rensa kundvagnen på allt innehåll
    @PostMapping("/clear")
    public String clearCart() {
        cartService.clearCart();
        return "redirect:/cart";
    }

    // checkout -skapa själva ordern
    @PostMapping("/checkout")
    // hämtar användaren som är inloggad i sessionen just nu mha @AuthenticationPrincipal,
    // den inloggade användaren injiceras direkt som parameter
    // UserDetails innehåller grundläggande info om användaren
    public String checkout(@AuthenticationPrincipal UserDetails userDetails) {
        Long orderId = orderService.createOrderFromCart(userDetails.getUsername());
        // URL som matchas mot OrderController,confirmation.html skapas i branch med OrderController
        return "redirect:/order/confirmation/" + orderId;  // dynamiskt id
    }


}
