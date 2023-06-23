package ru.sber.model;

import java.time.LocalDate;

/**
 * Класс для описания поэмы
 */
public record Poem(String author, String text, LocalDate createdDate) {

    /**
     * Публичный конструктор
     *
     * @param author
     * @param text
     */
    public Poem(String author, String text) {
        this(author, text, LocalDate.now());
    }

    @Override
    public String toString() {
        return "Poem{" +
                "createdDate=" + createdDate +
                ", author='" + author + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
