package ru.sber.models;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class Person {
    private final Parrot parrot1;
    private final Parrot parrot2;
    private final Dog dog;
    private final Cat cat;
    private String name;

    @Autowired
    public Person(Parrot parrot1, Parrot parrot2, Dog dog, Cat cat) {
        this.parrot1 = parrot1;
        this.parrot2 = parrot2;
        this.dog = dog;
        this.cat = cat;
    }

    @PostConstruct
    public void init() {
        this.name = "Master";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnimals() {
        return "\n\t" + parrot1 + ", \n\t" + parrot2 + ", \n\t" + dog + ", \n\t" + cat;
    }

}
