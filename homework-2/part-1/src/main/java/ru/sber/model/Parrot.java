package ru.sber.model;

/**
 * Класс попугая
 */
public record Parrot(String name) {
    public String toString() {
        return "Parrot { " + name + " }";
    }
}
