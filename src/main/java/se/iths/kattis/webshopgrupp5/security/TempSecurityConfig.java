package se.iths.kattis.webshopgrupp5.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


// denna tas bort när vi har riktig SecurityConfig på plats
@Configuration
@EnableWebSecurity
public class TempSecurityConfig {

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/testmail").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form.permitAll()
//                )
//                .logout(logout -> logout.permitAll());
//
//        return http.build();
//    }


    // kan användas vid test, stänger av Spring Security inloggningen
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
        );
        return http.build();
    }

}

