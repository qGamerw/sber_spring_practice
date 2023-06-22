package ru.sber.model;

import java.time.LocalDate;

/**
 * Класс для описания поэмы
 */
public class Poem {
    private final LocalDate createdDate;
    private String author;
    private String text;

    public Poem(String author, String text) {
        this.author = author;
        this.text = text;
        this.createdDate = LocalDate.now();
    }

    public String getText() {
        return text;
    }

    public String getAuthor() {
        return author;
    }


    public void setText(String text) {
        this.text = text;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
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
