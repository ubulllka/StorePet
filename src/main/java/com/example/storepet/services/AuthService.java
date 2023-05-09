package com.example.storepet.services;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.storepet.models.Person;
import com.example.storepet.security.PersonDetails;

import java.util.Optional;

@Service
public class AuthService {
    public Optional<Person> getPerson() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Person> optionalPerson = Optional.empty();
        if(!(authentication instanceof AnonymousAuthenticationToken)) {
            PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
            optionalPerson = Optional.of(personDetails.getPerson());
        }
        return optionalPerson;
    }

    public PersonDetails getPersonDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (PersonDetails) authentication.getPrincipal();
    }
}




