package ru.sber.models;

import org.springframework.stereotype.Component;

@Component("kimi")
public class Parrot2 implements IAnimal, IParrot {
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
