package se.iths.kattis.webshopgrupp5.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.iths.kattis.webshopgrupp5.model.AppUser;
import se.iths.kattis.webshopgrupp5.repository.AppUserRepository;

@Service //En implementation av Spring security interface UserDetailsService
public class MyUserDetailsService implements UserDetailsService {
    private final AppUserRepository appUserRepository;

    //Tar in AppUserRepository via dependency injection
    public MyUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override // Spring anropar denna method automatiskt
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Hämta användaren från databasen
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Username not found"));

        //Bygg ett UserDetails objekt
        return User.builder()
                .username(appUser.getUsername())
                .password(appUser.getPassword())
                .roles(appUser.getRole())
                .build();
    };
}
