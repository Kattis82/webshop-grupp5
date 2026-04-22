package se.iths.kattis.webshopgrupp5.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import se.iths.kattis.webshopgrupp5.model.AppUser;
import se.iths.kattis.webshopgrupp5.service.AppUserService;

import java.util.Optional;

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
    public String showProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        if (userDetails == null) {
            return "redirect:/home";
        }
        // - Hämta inloggad användare
        Optional<AppUser> userOptional = service.findByUsername(userDetails.getUsername());

        // - Hämta användare från Optional
        AppUser user = userOptional.orElse(null);

        // - Skicka med användare till profilsidan
        model.addAttribute("user", user);

        return "profile";
    }

    // Metod - Ta bort konto
    @PostMapping("/delete")
    public String deleteAccount(@AuthenticationPrincipal UserDetails userDetails, HttpSession session) {

        if (userDetails == null) {
            return "redirect:/home";
        }
        // - Ta bort användare via username
        service.deleteByUsername(userDetails.getUsername());

        // - Avsluta session
        session.invalidate();

        return "redirect:/?delete=true";
    }
}
