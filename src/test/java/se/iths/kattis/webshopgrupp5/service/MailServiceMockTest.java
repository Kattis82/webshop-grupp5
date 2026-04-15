package se.iths.kattis.webshopgrupp5.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.iths.kattis.mailservicegrupp5.model.Email;
import se.iths.kattis.mailservicegrupp5.service.EmailService;
import se.iths.kattis.webshopgrupp5.model.Order;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MailServiceMockTest {

    //låtsas objekt som injiceras
    @Mock
    EmailService emailService;

    // riktig MailService
    @InjectMocks
    MailService mailService;

    Order order;

    @BeforeEach
    void setUp() {
        order = new Order("kattis@test.com", 100.0, LocalDate.now());
        order.setId(1L);
    }

    @Test
    @DisplayName("Ska testa att ett mail skickas")
    void testSendOrderConfirmation() {
        // act - kör metoden i MailService
        mailService.sendOrderConfirmation("kattis@test.com", order);

        // verify - kollar så att send() i emailService anropas (void-metod)
        // med vilket Email-objekt som helst (inte ett exakt objekt)
        verify(emailService).send(any(Email.class));
    }

    @Test
    @DisplayName("Ska testa att mail skickas")
    void testSendProfileExport() {
        // act - kör metoden i MailService
        mailService.sendProfileExport("kattis@test.com", "test export data");

        // verify - kollar så att send() i emailService anropas (void-metod)
        // med vilket Email-objekt som helst (inte ett exakt objekt)
        verify(emailService).send(any(Email.class));

    }
}