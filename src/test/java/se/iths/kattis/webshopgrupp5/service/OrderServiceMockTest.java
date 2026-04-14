package se.iths.kattis.webshopgrupp5.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import se.iths.kattis.webshopgrupp5.exception.OrderNotFoundException;
import se.iths.kattis.webshopgrupp5.model.AppUser;
import se.iths.kattis.webshopgrupp5.model.Order;
import se.iths.kattis.webshopgrupp5.model.cart.CartItem;
import se.iths.kattis.webshopgrupp5.repository.AppUserRepository;
import se.iths.kattis.webshopgrupp5.repository.OrderRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceMockTest {

    // fejkade objekt som ska injiceras OrderService
    @Mock
    OrderRepository orderRepository;
    @Mock
    AppUserRepository appUserRepository;
    @Mock
    CartService cartService;
    @Mock
    MailService mailService;

    // riktig OrderService
    @InjectMocks
    OrderService orderService;

    //objekt till test
    AppUser testUser;
    Order testOrder;

    @BeforeEach
    void setUp() {
        testUser = new AppUser("kattis@test.com", "password", true, "USER");
        testUser.setId(1L);

        testOrder = new Order("kattis@test.com", 105.5, LocalDate.now());
        testOrder.setId(1L);
    }

    @DisplayName("Ska testa att order skapas korrekt")
    @Test
    void createOrderFromCartSuccess() {
        // arrange
        CartItem cartItem = new CartItem(1l, "testprodukt", 50.0, 2);

        // när metoden anropas...gör/returnera detta istället
        when(cartService.getCartItems()).thenReturn(List.of(cartItem));
        when(cartService.getTotalPrice()).thenReturn(100.0);
        when(appUserRepository.findByUsername("kattis@test.com"))
                .thenReturn(Optional.of(testUser));
        when(orderRepository.save(any(Order.class))).thenReturn(testOrder);

        // act - testar metoden createOrderFromCart
        Long orderId = orderService.createOrderFromCart("kattis@test.com");

        // assert - returneras rätt id
        Assertions.assertEquals(1L, orderId);

        // kontrollerar att rätt metoder anropas (save/sendOrderConfirmation/clearCart)
        verify(orderRepository).save(any(Order.class));
        verify(mailService).sendOrderConfirmation(eq("kattis@test.com"),
                any(Order.class));
        verify(cartService).clearCart();

    }

    @DisplayName("Ska testa att exception kastas när username inte hittas")
    @Test
    void createOrderFromCartThrowsException() {
        // arrange - när metoden anropas...gör/returnera detta istället
        when(cartService.getCartItems()).thenReturn(List.of()); // tom lista
        when(cartService.getTotalPrice()).thenReturn(0.0);
        when(appUserRepository.findByUsername("unknown@test.com")).
                thenReturn(Optional.empty()); // när user saknas

        // act and assert - verifierar att rätt exception kastas
        // när createOrderFromCart anropas
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> orderService.createOrderFromCart("unknown@test.com"));
    }

    @Test
    @DisplayName("Ska testa att order skapas korrekt")
    void getOrderByIdSuccess() {
        // arrange - när metoden anropas...returnera testOrder istället
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));

        // act - testar metoden getOrderById
        Order result = orderService.getOrderById(1L);

        // assert - kollar om rätt id och rätt username returneras
        Assertions.assertEquals(1l, result.getId());
        Assertions.assertEquals("kattis@test.com", result.getUsername());
    }

    @Test
    @DisplayName("Ska testa att exception kastas när id inte hittas")
    void getOrderByIdThrowsException() {
        // arrange - när metoden anropas...returnera order saknas
        when(orderRepository.findById(50L)).thenReturn(Optional.empty());

        // act och assert - verifierar att rätt exception kastas när
        // metoden getOrderById körs
        Assertions.assertThrows(OrderNotFoundException.class,
                () -> orderService.getOrderById(50L));

    }


    @DisplayName("Ska testa att kundvagnen töms efter order gjorts")
    @Test
    void createOrderFromCartThenClear() {
        // arrange
        CartItem cartItem = new CartItem(1l, "testprodukt", 50.0, 2);

        // när metoden anropas...gör/returnera detta istället
        when(cartService.getCartItems()).thenReturn(List.of(cartItem));
        when(cartService.getTotalPrice()).thenReturn(100.0);
        when(appUserRepository.findByUsername("kattis@test.com"))
                .thenReturn(Optional.of(testUser));
        when(orderRepository.save(any(Order.class))).thenReturn(testOrder);


        // act - skapa order
        orderService.createOrderFromCart("kattis@test.com");

        // assert - verifierar att kundvagnen töms efter order har skapats
        verify(cartService).clearCart();

    }
}