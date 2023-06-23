package ru.sber.model;

import org.springframework.stereotype.Component;

/**
 * Класс попугая 2
 */
@Component("kimi")
public class Parrot2 implements parrotInterface {
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
