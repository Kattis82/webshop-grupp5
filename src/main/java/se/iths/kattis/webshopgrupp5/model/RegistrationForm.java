package se.iths.kattis.webshopgrupp5.model;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationForm {

    // Variabler
    @NotBlank(message = "(!) Email måste fyllas i")
    @Email(message = "Ogiltig email!")
    private String username;

    @NotBlank(message = "(!) Lösenord måste fyllas i")
    @Size(min = 6, message = "Minst 6 tecken!")
    private String password;

    @AssertTrue(message = "Du måste godkänna villkoren!")
    private boolean consent;
}