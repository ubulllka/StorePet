package com.example.storepet.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.storepet.models.Product;
import com.example.storepet.models.Person;

@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {
    private int id;
    private Person person;
    private Product product;
    private int count;
}



