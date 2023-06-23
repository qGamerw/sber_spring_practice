package ru.sber.model;

import org.springframework.stereotype.Component;

/**
 * Класс попугая 1
 */
@Component("Koko")
public class Parrot1 implements parrotInterface {
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
        return "Parrot{ " + name + " }";
    }
}
