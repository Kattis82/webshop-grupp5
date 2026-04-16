package se.iths.kattis.webshopgrupp5.util;

import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import se.iths.kattis.webshopgrupp5.model.AppUser;
import se.iths.kattis.webshopgrupp5.repository.AppUserRepository;

@Component
public class DatabaseInit {

    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository appUserRepository;

    public DatabaseInit(PasswordEncoder passWordEncoder, AppUserRepository appUserRepository) {
        this.passwordEncoder = passWordEncoder;
        this.appUserRepository = appUserRepository;
    }

    @PostConstruct
    public void createUser() {
        AppUser appUser = new AppUser();
        appUser.setUsername("paveena.naebsuwan@gmail.com");
        appUser.setRole("ADMIN");
        appUser.setPassword(passwordEncoder.encode("password"));
        appUser.setConsent(true);
        appUserRepository.save(appUser);

        AppUser appUser2 = new AppUser();
        appUser2.setUsername("josefine.m.berglund@gmail.com");
        appUser2.setRole("ADMIN");
        appUser2.setPassword(passwordEncoder.encode("password"));
        appUser2.setConsent(true);
        appUserRepository.save(appUser2);

        AppUser appUser3 = new AppUser();
        appUser3.setUsername("kattiscalmvik@gmail.com");
        appUser3.setRole("ADMIN");
        appUser3.setPassword(passwordEncoder.encode("password"));
        appUser3.setConsent(true);
        appUserRepository.save(appUser3);

        AppUser appUser4 = new AppUser();
        appUser4.setUsername("jh.viippa@gmail.com");
        appUser4.setRole("ADMIN");
        appUser4.setPassword(passwordEncoder.encode("password"));
        appUser4.setConsent(true);
        appUserRepository.save(appUser4);

    }
}
