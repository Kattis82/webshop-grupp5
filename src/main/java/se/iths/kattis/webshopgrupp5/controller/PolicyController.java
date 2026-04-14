package se.iths.kattis.webshopgrupp5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/policy")
public class PolicyController {

    @GetMapping("/privacy")
    public String privacyPolicy() {
        return "privacy";
    }

    @GetMapping("/cookie")
    public String cookiePolicy() {
        return "cookie";
    }
}
