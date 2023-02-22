package ua.socialnetwork.controller;

import org.atmosphere.config.service.Get;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {

        return "login-page";
    }



}
