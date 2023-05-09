package com.example.storepet.models;

public enum Animal {
    CAT("Кошки"),
    DOG("Собаки"),
    BIRD("Птицы"),
    RODENT("Грызуны"),
    OTHER("Другие");
    String name;
    Animal(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name;
    }
}



