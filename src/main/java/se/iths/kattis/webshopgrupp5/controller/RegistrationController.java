package se.iths.kattis.webshopgrupp5.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import se.iths.kattis.webshopgrupp5.model.RegistrationForm;
import se.iths.kattis.webshopgrupp5.service.AppUserService;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    // Variabler
    private final AppUserService service;

    // Konstruktor
    public RegistrationController(AppUserService service) {
        this.service = service;
    }

    // Metod - Hämta registreringsformulär
    @GetMapping
    public String fetchForm(Model model) {
        model.addAttribute("form", new RegistrationForm());
        return "register";
    }

    // Metod - Hantera registrering
    @PostMapping
    public String handleForm(@Valid @ModelAttribute("form") RegistrationForm form,
                             BindingResult result,
                             Model model) {

        // Ogiltig input
        if (result.hasErrors()) {
            model.addAttribute("error", "Error - Ogiltig input!");
            return "register";
        }

        // Användare finns redan
        if (service.existsByUsername(form.getUsername())) {
            model.addAttribute("error", "Användaren finns redan!");
            return "register";
        }

        // Registrera en ny användare
        service.register(
                form.getUsername(),
                form.getPassword(),
                form.isConsent()
        );
        return "redirect:/";
    }
}
