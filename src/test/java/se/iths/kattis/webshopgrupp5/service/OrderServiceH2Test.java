package se.iths.kattis.webshopgrupp5.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import se.iths.kattis.webshopgrupp5.exception.OrderNotFoundException;
import se.iths.kattis.webshopgrupp5.model.AppUser;
import se.iths.kattis.webshopgrupp5.model.Order;
import se.iths.kattis.webshopgrupp5.model.cart.CartItem;
import se.iths.kattis.webshopgrupp5.repository.AppUserRepository;
import se.iths.kattis.webshopgrupp5.repository.OrderRepository;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;

// testar OrderService med en riktig databas

@SpringBootTest // startar hela Spring Boot context
@ActiveProfiles("test") // aktiverar application-test.properties
class OrderServiceH2Test {

    @Autowired // injicerar riktiga beans
    OrderService orderService;
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    OrderRepository orderRepository;

    // mockas så att de inte påverkar testet
    @MockitoBean // mockar - blir låtsasobjekt
            CartService cartService;
    @MockitoBean
    MailService mailService;

    AppUser testUser;

    @BeforeEach
    void setUp() {
        // rensar databaserna
        orderRepository.deleteAll();
        appUserRepository.deleteAll();

        testUser = new AppUser("kattis@test.com", "password",
                true, "USER");
        // testUser sparas i databasen
        appUserRepository.save(testUser);
    }

    @Test
    @Transactional
    // för att lösa problem med lazyloading problem
    // (orderItems - getOrderItems är tom om man inte är inom samma transaktion)
    @DisplayName("Ska testa att order sparas i databasen")
    void createOrderFromCartSuccess() {
        // arrange
        CartItem cartItem = new CartItem(1l, "testprodukt", 50.0, 2);

        // när metoden anropas...gör detta istället
        when(cartService.getCartItems()).thenReturn(List.of(cartItem));
        when(cartService.getTotalPrice()).thenReturn(100.0);

        // act - kör createOrderFromCart, ordern sparas i riktig databas
        Long orderId = orderService.createOrderFromCart("kattis@test.com");

        // hämtar ordern från databasen
        Order savedOrder = orderRepository.findById(orderId).orElseThrow();
        // assert - kollar så att username,totalpris, antal cartItems och orderdatum stämmer
        Assertions.assertEquals("kattis@test.com", savedOrder.getUsername());
        Assertions.assertEquals(100.0, savedOrder.getTotalPrice());
        Assertions.assertEquals(1, savedOrder.getOrderItems().size());
        Assertions.assertEquals(LocalDate.now(), savedOrder.getOrderDate());
    }

    @Test
    @DisplayName("Ska testa att order hämtas från databasen")
    void getOrderByIdSuccess() {
        // arrange - testdata för order som sparas i databasen
        Order order = new Order("kattis@test.com", 100.0, LocalDate.now());
        order.setUser(testUser);
        Order savedOrder = orderRepository.save(order);

        // act - getOrderById körs, hämtar ordern från databasen via orderService
        Order result = orderService.getOrderById(savedOrder.getId());

        // assert - kollar så att det är förväntat order id samt username
        Assertions.assertEquals(savedOrder.getId(), result.getId());
        Assertions.assertEquals("kattis@test.com", result.getUsername());

    }

    @Test
    @DisplayName("Ska testa att exception kastas om id inte finns i databasen")
    void getOrderByIdThrowsException() {
        // act och assert - verifierar att rätt exception kastas när
        // metoden getOrderById körs
        Assertions.assertThrows(OrderNotFoundException.class,
                () -> orderService.getOrderById(50L));
    }
}