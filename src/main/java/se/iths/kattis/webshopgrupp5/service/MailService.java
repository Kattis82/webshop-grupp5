package se.iths.kattis.webshopgrupp5.service;

import org.springframework.stereotype.Service;
import se.iths.kattis.mailservicegrupp5.model.Email;
import se.iths.kattis.mailservicegrupp5.service.EmailService;
import se.iths.kattis.webshopgrupp5.model.Order;

@Service
public class MailService {

    // via EmailService får man tillgång till EmailSender och metoden send
    // som hanterar funktionaliteten för att skicka ett gmail
    private final EmailService emailService;

    public MailService(EmailService emailService) {
        this.emailService = emailService;
    }

    // metod som skickar en orderbekräftelse, ett Email skapas med mottagarens
    // mailadress, med order id i ämnesraden och
    // skriver ut totalpriset för ordern i meddelande-fältet
    public void sendOrderConfirmation(String recipient, Order order) {
        emailService.send(new Email(recipient, "Orderbekräftelse #" + order.getId(),
                "Tack för din beställning! Totalt: " + order.getTotalPrice() + " kr"));
    }

    // metod förberedd för nedan:
    // följande funktion ska finnas kopplad till användarens profil:
    // möjlighet att få sina uppgifter mejlade till sig
    public void sendProfileExport(String recipient, String exportData) {
        emailService.send((new Email(recipient, "Din personliga data", exportData)));
    }
}
