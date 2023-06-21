package ru.sber.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Person {
    private final Cat cat;
    private final IParrot parrot1;
    private final IParrot parrot2;
    private final Dog dog;
    private String name;

    @Autowired
    public Person(Cat cat, @Qualifier("Koko") IParrot parrot1, @Qualifier("kimi") IParrot parrot2, Dog dog) {
        this.cat = cat;
        this.parrot1 = parrot1;
        this.parrot2 = parrot2;
        this.dog = dog;
    }

    @PostConstruct
    public void init() {
        this.name = "Master";
        this.cat.setName("Murky");
        this.parrot1.setName("Koko");
        this.parrot2.setName("Kimi");
        this.dog.setName("Bobi");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnimals() {
        return "\n\t" + cat + ", \n\t" + parrot1 + ", \n\t" + parrot2 + ", \n\t" + dog;
    }
}
