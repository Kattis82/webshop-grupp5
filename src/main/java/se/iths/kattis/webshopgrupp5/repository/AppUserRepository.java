package se.iths.kattis.webshopgrupp5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.kattis.webshopgrupp5.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, String> {

    // Metod - Kontrollerar att username (email) finns
    boolean existsByUsername(String username);

    // Metod - Hämtar användare via username (email)
    AppUser findByUsername(String username);

    // Metod - Sparar användare
    AppUser save(AppUser user);

    // Metod - Tar bort användare
    void deleteByUsername(String username);
}
