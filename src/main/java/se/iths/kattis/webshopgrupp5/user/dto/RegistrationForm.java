package se.iths.kattis.webshopgrupp5.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegistrationForm {

    // Attributes
    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 10)
    private String password;

    private boolean consent;

    // Constructor - Empty
    public RegistrationForm() {
    }

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isConsent() {
        return consent;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConsent(boolean consent) {
        this.consent = consent;
    }
}
