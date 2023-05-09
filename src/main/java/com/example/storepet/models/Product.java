package com.example.storepet.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private int price;
    @Column(name = "count")
    private int count;
    @Column(name = "description")
    private String description;
    @Enumerated(EnumType.STRING)
    private Animal animal;
    @Enumerated(EnumType.STRING)
    private TypeProduct typeProduct;
    @ManyToMany(mappedBy = "likedProducts")
    private List<Person> likedPerson;
    @ManyToMany(mappedBy = "orderedProducts")
    private List<Person> orderedPerson;
    public void addLikedPerson(Person person) {
        if(likedPerson == null) likedPerson = new ArrayList<>();
        likedPerson.add(person);
    }
}






