package ru.sber.model;

/**
 * Класс для отправки ошибки на запрос
 */
public class ErrorDetails {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
