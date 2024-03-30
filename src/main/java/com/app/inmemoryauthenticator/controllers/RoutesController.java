package com.app.inmemoryauthenticator.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoutesController {

    @GetMapping("/")
    public String home(){
        return "Home Page - Allowed for everyone";
    }

    @PostMapping("/login")
    public String login(){
        return "Login Page - Allowed for everyone";
    }

    @GetMapping("/users")
    public String users(){
        return "Users Page - Allowed for logged-in users and administrators";
    }

    @GetMapping("/admins")
    public String admins(){
        return "Admins Page - Allowed for logged-in admins";
    }

    @GetMapping("/accessDenied")
    public String accessDenied(){
        return "Access denied Page - Allowed for all";
    }

}
