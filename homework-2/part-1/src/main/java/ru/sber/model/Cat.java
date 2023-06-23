package ru.sber.model;

/**
 * Класс кота
 */
public record Cat(String name) {
    public String toString() {
        return "Cat { " + name + " }";
    }
}
