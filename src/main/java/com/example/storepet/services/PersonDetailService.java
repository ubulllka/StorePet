package com.example.storepet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.storepet.models.Person;
import com.example.storepet.repositories.PeopleDetailRepository;
import com.example.storepet.security.PersonDetails;

import java.util.Optional;

@Service
public class PersonDetailService implements UserDetailsService {
    private final PeopleDetailRepository peopleDetailRepository;

    @Autowired
    public PersonDetailService(PeopleDetailRepository peopleDetailRepository) {
        this.peopleDetailRepository = peopleDetailRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = peopleDetailRepository.findByEmail(username);
        if(person.isEmpty())
            throw new UsernameNotFoundException("user not found");
        return new PersonDetails(person.get());
    }
}


