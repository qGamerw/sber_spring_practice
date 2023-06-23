package ru.sber.model;

import org.springframework.stereotype.Component;

/**
 * Класс кота
 */
@Component
public class Cat implements animalInterface {
    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Cat{ " + name + " }";
    }
}