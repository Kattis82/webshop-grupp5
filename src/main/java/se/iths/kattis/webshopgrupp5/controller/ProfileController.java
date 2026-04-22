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
import se.iths.kattis.webshopgrupp5.service.CartService;
import se.iths.kattis.webshopgrupp5.service.MailService;

import java.util.Optional;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    // Variabler
    private final AppUserService service;
    private final CartService cartService;
    private final MailService mailService;

    // Konstruktor
    public ProfileController(AppUserService service, CartService cartService, MailService mailService) {
        this.service = service;
        this.cartService = cartService;
        this.mailService = mailService;
    }

    // Metod - Visa profilsidan
    @GetMapping
    public String showProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        if (userDetails == null) {
            return "redirect:/home";
        }

        // - Hämta inloggad användare
        Optional<AppUser> userOptional = service.findByUsername(userDetails.getUsername());

        // - Hämta användare (Optional)
        AppUser user = userOptional.orElse(null);

        // - Skicka med användare till profilsidan
        model.addAttribute("user", user);

        return "profile";
    }

    // Metod - Exportera användardata
    @PostMapping("/export")
    public String exportAccount(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        if (userDetails == null) {
            return "redirect:/home";
        }

        // - Hämta inloggad användare
        Optional<AppUser> userOptional = service.findByUsername(userDetails.getUsername());
        AppUser user = userOptional.orElse(null);

        if (user == null) {
            return "redirect:/profile?error=true";
        }

        // - Mejlets innehåll (användardata)
        String exportData = "Dina uppgifter:\n" +
                "Email: " + user.getUsername() + "\n" +
                "Roll: " + user.getRole() + "\n" +
                "Godkänt villkor: " + user.isConsent() + "\n\n" +
                "Exporterad: " + java.time.LocalDateTime.now();

        // - Skicka mejl (användardata)
        mailService.sendProfileExport(user.getUsername(), exportData);

        return "redirect:/profile?exported=true";
    }

    // Metod - Ta bort konto
    @PostMapping("/delete")
    public String deleteAccount(@AuthenticationPrincipal UserDetails userDetails, HttpSession session) {

        if (userDetails == null) {
            return "redirect:/home";
        }

        // - Rensa kundvagnen vid delete
        cartService.clearCart();

        // - Ta bort användare via username
        service.deleteByUsername(userDetails.getUsername());

        // - Avsluta session
        session.invalidate();

        return "redirect:/?delete=true";
    }
}