package com.example.storepet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.storepet.models.Person;
import com.example.storepet.repositories.PersonRepository;

@Service
public class PersonRegistrationService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonRegistrationService(PersonRepository repository, PasswordEncoder encoder) {
        this.personRepository = repository;
        this.passwordEncoder = encoder;
    }
    @Transactional
    public void registration(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole("ROLE_USER");
        personRepository.save(person);
    }
}


