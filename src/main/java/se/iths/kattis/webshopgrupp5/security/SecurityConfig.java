package se.iths.kattis.webshopgrupp5.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authorization.EnableMultiFactorAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.FactorGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Detta är en konfigurationsklass
@EnableWebSecurity // Aktiverar Spring Security i applikationen
@EnableMultiFactorAuthentication(
        authorities = {
                FactorGrantedAuthority.PASSWORD_AUTHORITY, // Första faktor: lösenord
                FactorGrantedAuthority.OTT_AUTHORITY       // Andra faktor: One-Time Token (2FA via email)
        }
)
public class SecurityConfig {

    // Service som laddar användare från databasen (kopplas till login)
    private final MyUserDetailsService userDetailsService;

    // Handler som körs när en OTT-token genereras (skickar email med länk)
    private final OttSuccessHandler ottSuccessHandler;

    // Constructor injection (Spring skickar in dependencies automatiskt)
    public SecurityConfig(MyUserDetailsService userDetailsService, OttSuccessHandler ottSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.ottSuccessHandler = ottSuccessHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // Kopplar vår egen UserDetailsService till Spring Security
        http.userDetailsService(userDetailsService);

        http
                // Authorization, vem får tillgång till vad
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers(
                                        "/",                  // startsida (öppen)
                                        "/login/**",// login
                                        "/register**",
                                        "/consent",
                                        "/login/ott",// 2FA endpoints
                                        "/policy/privacy", "/policy/cookie" // policy-sidor

                                ).permitAll() // alla ovan når utan inloggning

                                // Endast användare med rollen ADMIN får access
                                .requestMatchers("/admin/**").hasRole("ADMIN")

                                // Alla andra endpoints kräver inloggning
                                .anyRequest().authenticated()
                )

                // Login (första steg i autentisering)
                .formLogin(form ->
                        form.defaultSuccessUrl("/products", true)
                )

                // 2FA (andra steg – One-Time Token via email)
                .oneTimeTokenLogin(ott -> ott
                                .tokenGenerationSuccessHandler(ottSuccessHandler)
                        /* När password är korrekt:
                            1. Spring genererar token
                            2. OttSuccessHandler skickar mail med länk */
                )

                // Logout
                .logout(logout -> logout
                        .logoutUrl("/logout") // endpoint för logout
                        .logoutSuccessUrl("/?logout=true") // redirect efter logout
                        .invalidateHttpSession(true) // rensar session
                        .deleteCookies("JSESSIONID") // tar bort session-cookie
                        .permitAll()
                );

        return http.build(); // bygger och returnerar security-konfigurationen
    }

    // Bean som används av Spring för autentisering (kopplar ihop login-systemet)
    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Bean för att hasha lösenord (BCrypt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}