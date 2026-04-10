package se.iths.kattis.webshopgrupp5.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.iths.kattis.webshopgrupp5.exception.OrderNotFoundException;
import se.iths.kattis.webshopgrupp5.model.AppUser;
import se.iths.kattis.webshopgrupp5.model.Order;
import se.iths.kattis.webshopgrupp5.model.OrderItem;
import se.iths.kattis.webshopgrupp5.model.cart.CartItem;
import se.iths.kattis.webshopgrupp5.repository.AppUserRepository;
import se.iths.kattis.webshopgrupp5.repository.OrderRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final AppUserRepository appUserRepository;
    private final MailService mailService;

    public OrderService(OrderRepository orderRepository, CartService cartService, AppUserRepository appUserRepository, MailService mailService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.appUserRepository = appUserRepository;
        this.mailService = mailService;
    }

    // metod som skapar en order av det som ligger i kundvagnen
    public Long createOrderFromCart(String username) {

        // hämtar alla rader som ligger i kundvagnen
        List<CartItem> cartItems = cartService.getCartItems();

        // skapar ett nytt order-objekt med användarnamn (mail), totalpris
        // från kundvagnen och dagens datum
        Order order = new Order(username, cartService.getTotalPrice(), LocalDate.now());

        // hämtar användaren från databasen eller kastar exception om
        // användaren inte hittas
        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Användaren hittades inte"));
        // kopplar användaren till ordern
        order.setUser(user);

        // går igenom varje rad i kundvagnen
        for (CartItem cartItem : cartItems) {
            // och skapar en OrderItem av den
            OrderItem orderItem = new OrderItem(
                    cartItem.getProductName(),
                    cartItem.getQuantity(),
                    cartItem.getProductPrice()
            );
            // OrderItem läggs till denna order
            order.addOrderItem(orderItem);
        }

        // ordern sparas i databasen
        Order savedOrder = orderRepository.save(order);
        // skickar bekräftelsemail
        mailService.sendOrderConfirmation(username, savedOrder);
        // tömmer kundvagnen
        cartService.clearCart();

        // returnerar orderns id
        return savedOrder.getId();
    }

    // metod som hämtar en specifik order från databasen med hjälp av id
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order med ordernummer " + id + " kunde inte hittas"));
    }
}
