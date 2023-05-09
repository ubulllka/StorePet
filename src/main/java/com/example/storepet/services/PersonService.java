package com.example.storepet.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.storepet.models.Product;
import com.example.storepet.models.Person;
import com.example.storepet.repositories.PersonRepository;

import java.util.List;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person getPerson(int id) {
        return personRepository.findById(id).orElse(null);
    }

    public List<Person> findByName(String name) {
        return personRepository.findByNameStartsWithIgnoreCase(name);
    }

    @Transactional
    public void update(int id, Person newPerson) {
        Person oldPerson = personRepository.findById(id).get();
        oldPerson.setName(newPerson.getName());
        oldPerson.setEmail(newPerson.getEmail());
        personRepository.save(oldPerson);
    }

    @Transactional
    public void addLikedProduct(int id, Product product) {
        Person person = personRepository.findById(id).get();
        Hibernate.initialize(person.getLikedProducts());
        person.addLikedProduct(product);
    }

    public List<Product> getLikedProducts(int id) {
        return personRepository.findById(id).get().getLikedProducts();
    }

    @Transactional
    public void deleteLikedProduct(int id, Product product) {
        Person person = personRepository.findById(id).get();
        person.getLikedProducts().remove(product);
    }

}
