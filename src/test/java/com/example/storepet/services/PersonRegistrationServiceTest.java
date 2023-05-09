package com.example.storepet.services;

import com.example.storepet.models.Person;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class PersonRegistrationServiceTest {

    @Mock
    private PersonRegistrationService personRegistrationService;

    @Test
    void registration() {
        Person person = new Person();

        personRegistrationService.registration(person);
        verify(personRegistrationService, times(1)).registration(person);
    }
}



