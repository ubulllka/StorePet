package com.example.storepet.models;

public enum TypeProduct {
    FOOD("Еда"),
    CARE("Уход"),
    MEDICINE("Лекарство"),
    CLOTHES("Одежда"),
    TREAT("Лакомство");
    String name;
    TypeProduct(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name;
    }
}



