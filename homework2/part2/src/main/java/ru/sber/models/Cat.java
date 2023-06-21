package ru.sber.models;

import org.springframework.stereotype.Component;

@Component
public class Cat implements IAnimal {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Cat{ " + name + " }";
    }
}