package ru.sber.model;

/**
 * Класс собаки
 */
public record Dog(String name) {
    public String toString() {
        return "Dog { " + name + " }";
    }
}
