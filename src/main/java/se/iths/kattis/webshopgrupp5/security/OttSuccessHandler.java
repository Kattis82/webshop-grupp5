package se.iths.kattis.webshopgrupp5.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.ott.OneTimeToken;
import org.springframework.security.web.authentication.ott.OneTimeTokenGenerationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import se.iths.kattis.mailservicegrupp5.model.Email;
import se.iths.kattis.mailservicegrupp5.service.EmailService;

import java.io.IOException;

@Component // Gör klassen till en Spring Bean så den kan användas i SecurityConfig
public class OttSuccessHandler implements OneTimeTokenGenerationSuccessHandler {

    // EmailService används för att skicka mail från separat projekt
    private final EmailService emailService;



    // Constructor injection, Spring skickar in EmailService automatiskt
    public OttSuccessHandler(EmailService emailService) {
        this.emailService = emailService;
    }

    /* Denna metod körs automatiskt efter att användaren loggat in med
       username och password, men innan inloggningen är helt klar.
       Här sker steg 2 i tvåfaktorsautentisering (2FA). */
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       OneTimeToken oneTimeToken) throws IOException, ServletException {

        // Skapar en länk som innehåller den unika tokenen
        // Användaren måste klicka på denna länk för att verifiera sin inloggning
        String link = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/login/ott") // Spring endpoint som verifierar token
                .queryParam("token", oneTimeToken.getTokenValue()) // lägger till token i URL
                .toUriString();

        // Skickar ett mejl till användaren med länken
        // oneTimeToken.getUsername() = användarens e-post

        emailService.send(new Email(
                request.getParameter("username"), // hämtar username (e-post) från login-formuläret
                "Inloggning",
                link
        ));

        /*Skickar tillbaka användaren till home
         där användaren väntar på att klicka på länken i mejlet */
        response.sendRedirect("/");
    }
}