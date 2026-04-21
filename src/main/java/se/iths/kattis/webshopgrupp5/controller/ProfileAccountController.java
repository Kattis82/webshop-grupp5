package se.iths.kattis.webshopgrupp5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import se.iths.kattis.webshopgrupp5.model.AppUser;
import se.iths.kattis.webshopgrupp5.service.AppUserService;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ProfileAccountController {

    // Variabler
    private final AppUserService service;

    // Konstruktor
    public ProfileAccountController(AppUserService service) {
        this.service = service;
    }

    // Metod - Exportera användardata
    @GetMapping("/export")
    public String exportUserData(Principal principal, Model model) {

        // - Hämta inloggad användare
        AppUser user = service.findByUsername(principal.getName()).orElseThrow();

        // - Skicka med användaren till exportsidan
        model.addAttribute("user", user);

        // - Visa exportsidan
        return "profile-export";
    }

    // Metod - Ta bort konto
    @PostMapping("/delete")
    public String deleteAccount(Principal principal) {

        // - Hämta inloggad användare
        var username = principal.getName();

        // - Ta bort användare
        service.deleteByUsername(username);

        // - Skicka tillbaka till startsidan
        return "redirect:/";
    }
}