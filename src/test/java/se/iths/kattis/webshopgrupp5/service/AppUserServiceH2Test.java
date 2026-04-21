package se.iths.kattis.webshopgrupp5.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import se.iths.kattis.webshopgrupp5.model.AppUser;
import se.iths.kattis.webshopgrupp5.repository.AppUserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class AppUserServiceH2Test {

    // Repository
    @Autowired
    AppUserRepository repository;

    // Testmetod - Sparar user i databasen
    @Test
    @DisplayName("Spara användare i databasen")
    void saveCreatesUser() {

        // Arrange
        AppUser user = new AppUser("newuser@testmail.com", "123", true, "USER");

        // Act
        AppUser saved = repository.save(user);

        // Assert
        assertNotNull(saved.getId());
        assertEquals("newuser@testmail.com", saved.getUsername());
        assertEquals("USER", saved.getRole());
    }

    // Testmetod - Hämtar user i databsen
    @Test
    @DisplayName("Hämta användare från databasen")
    void findReturnsUser() {

        // Arrange
        AppUser user = new AppUser("newuser@testmail.com", "123", true, "USER");
        repository.save(user);

        // Act
        Optional<AppUser> result = repository.findByUsername("newuser@testmail.com");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("newuser@testmail.com", result.get().getUsername());
    }

    // Testmetod - Tar bort användare från databasen
    @Test
    @DisplayName("Ta bort användare från databasen")
    void deleteRemovesUser() {

        // Arrange
        AppUser user = new AppUser("newuser@testmail.com", "123", true, "USER");
        repository.save(user);

        // Act
        repository.deleteByUsername("newuser@testmail.com");

        // Assert
        assertFalse(repository.findByUsername("newuser@testmail.com").isPresent());
    }

    // Testmetod - Användare finns databasen
    @Test
    @DisplayName("Returnera true om användare finns i databasen")
    void existsReturnsTrue() {

        //Arrange
        AppUser user = new AppUser("newuser@testmail.com", "123", true, "USER");
        repository.save(user);

        // Act
        boolean exists = repository.existsByUsername("newuser@testmail.com");

        // Assert
        assertTrue(exists);

    }

    // Testmetod - Användare saknas i databasen
    @Test
    @DisplayName("Returnera false om användare saknas i databasen")
    void existsReturnsFalse() {

        // Act
        boolean exists = repository.existsByUsername("missinguser@testmail.com");

        // Assert
        assertFalse(exists);
    }
}
