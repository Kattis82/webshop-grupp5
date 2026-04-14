package se.iths.kattis.webshopgrupp5.service;

import org.springframework.stereotype.Service;
import se.iths.kattis.webshopgrupp5.model.AppUser;
import se.iths.kattis.webshopgrupp5.repository.AppUserRepository;

import java.util.Optional;

@Service
public class AppUserService {

    // Variabler
    private AppUserRepository repository;

    // Konstruktor
    public AppUserService(AppUserRepository repository) {
        this.repository = repository;
    }

    // Metod - Registrar användare
    public void register(String username, String password, boolean consent) {
        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setConsent(consent);
        user.setRole("USER");
        repository.save(user);
    }

    // Metod - Hämtar via användare
    public Optional<AppUser> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    // Metod - Tar bort användare
    public void deleteByUsername(String username) {
        repository.deleteByUsername(username);
    }

    // Metod - Kollar om email existerar
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }
}