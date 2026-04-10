package se.iths.kattis.webshopgrupp5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import se.iths.kattis.mailservicegrupp5.model.Email;
import se.iths.kattis.mailservicegrupp5.service.EmailService;


// denna tas bort när vi har riktiga controllers och SecurityConfig på plats
@Controller
public class TestController {

    private final EmailService emailService;

    public TestController(EmailService emailService) {
        this.emailService = emailService;
    }


    @GetMapping("/testmail")
    @ResponseBody
    public String testMail() {

        emailService.send(
                new Email(
                        "kattiscalmvik@gmail.com",
                        "Test",
                        "Fungerar!"
                )
        );

        return "Mail skickat";
    }
}
