package se.iths.kattis.webshopgrupp5.util;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import se.iths.kattis.webshopgrupp5.model.AppUser;
import se.iths.kattis.webshopgrupp5.repository.AppUserRepository;

@Profile({"dev","test"})
@Component
public class DatabaseInit {

    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository appUserRepository;

    public DatabaseInit(PasswordEncoder passWordEncoder, AppUserRepository appUserRepository) {
        this.passwordEncoder = passWordEncoder;
        this.appUserRepository = appUserRepository;
    }

    @PostConstruct
    public void createUsers() {

        createAdminIfNotExists("paveena.naebsuwan@gmail.com");
        createAdminIfNotExists("josefine.m.berglund@gmail.com");
        createAdminIfNotExists("kattiscalmvik@gmail.com");
        createAdminIfNotExists("jh.viippa@gmail.com");
    }

    private void createAdminIfNotExists(String email) {

        if (appUserRepository.findByUsername(email).isEmpty()) {

            AppUser user = new AppUser();

            user.setUsername(email);
            user.setRole("ADMIN");
            user.setPassword(passwordEncoder.encode("password"));
            user.setConsent(true);

            appUserRepository.save(user);
        }
    }
}
