package se.iths.kattis.webshopgrupp5.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import se.iths.kattis.mailservicegrupp5.model.Email;
import se.iths.kattis.mailservicegrupp5.service.EmailService;
import se.iths.kattis.webshopgrupp5.model.Order;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest // startar hela Spring Boot context
@ActiveProfiles("test") // aktiverar application-test.properties
class MailServiceH2Test {

    @Autowired // injicerar riktiga beans
    MailService mailService;

    // mockas så att de inte påverkar testet
    @MockitoBean // mockar - blir låtsasobjekt
            EmailService emailService;

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