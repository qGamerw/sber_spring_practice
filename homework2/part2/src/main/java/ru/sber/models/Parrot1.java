package ru.sber.models;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component("Koko")
public class Parrot1 implements IAnimal, IParrot {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Parrot{ " + name + " }";
    }
}
