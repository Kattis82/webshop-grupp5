package se.iths.kattis.webshopgrupp5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.kattis.webshopgrupp5.model.AppUser;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, String> {

    // Metod - Kontrollerar att username (email) finns
    boolean existsByUsername(String username);

    // Metod - Hämtar användare via username (email)
    Optional<AppUser> findByUsername(String username);

    // Metod - Tar bort användare
    void deleteByUsername(String username);
}
