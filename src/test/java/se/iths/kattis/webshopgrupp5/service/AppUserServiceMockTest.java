package se.iths.kattis.webshopgrupp5.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import se.iths.kattis.webshopgrupp5.model.AppUser;
import se.iths.kattis.webshopgrupp5.repository.AppUserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AppUserServiceMockTest {

    // Mockat repository
    @Mock
    AppUserRepository repository;

    // Mockad encoder
    @Mock
    PasswordEncoder encoder;

    // App Service (mocks)
    @InjectMocks
    AppUserService service;

    // Testmetod - verifierar att lösenordet hashats
    @Test
    public void registerCreatesUserWithHash() {

        // Arrange
        Mockito.when(encoder.encode("123")).thenReturn("HASH");

        // Act
        service.register("hej@testmail.com", "123", true);

        // Assert
        verify(repository).save(Mockito.argThat(user ->
                user.getUsername().equals("hej@testmail.com") &&
                        user.getPassword().equals("HASH") &&
                        user.getRole().equals("USER")
        ));
    }

    // Testmetod - Hämtar user korrekt
    @Test
    void findReturnsUserFromRepository() {

        // Arrange
        AppUser user = new AppUser("a", "b", true, "USER");
        Mockito.when(repository.findByUsername("a")).thenReturn(Optional.of(user));

        // Act
        var result = service.findByUsername("a");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("a", result.get().getUsername());
    }

    // Testmetod - Kör delete i repository
    @Test
    void deleteUsesRepository() {

        // Act
        service.deleteByUsername("a");

        // Assert
        verify(repository).deleteByUsername("a");
    }

    // Testmetod - Kontrollerar exists via repository
    @Test
    void existsUsesRepository() {

        // Arrange
        Mockito.when(repository.existsByUsername("a")).thenReturn(true);

        // Act
        boolean exists = service.existsByUsername("a");

        // Assert
        assertTrue(exists);
    }
}