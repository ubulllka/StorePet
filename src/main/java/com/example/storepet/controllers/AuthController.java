package com.example.storepet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.storepet.models.Person;
import com.example.storepet.services.PersonRegistrationService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final PersonRegistrationService registrationService;

    @Autowired
    public AuthController(PersonRegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person")Person person) {
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("person")Person person) {
        registrationService.registration(person);
        return "login";
    }
}
