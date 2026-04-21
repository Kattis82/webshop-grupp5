package se.iths.kattis.webshopgrupp5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import se.iths.kattis.webshopgrupp5.service.AppUserService;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    // Variabler
    private final AppUserService service;

    // Konstruktor
    public ProfileController(AppUserService service) {
        this.service = service;
    }

    // Metod - Visa profilsidan
    @GetMapping
    public String showProfile(Principal principal, Model model) {

        // - Hämta inloggad användare
        var user = service.findByUsername(principal.getName()).orElse(null);

        // - Skicka med användare till profilsidan
        model.addAttribute("user", user);
        return "profile";
    }
}
