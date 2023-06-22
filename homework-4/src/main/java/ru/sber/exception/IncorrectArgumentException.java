package ru.sber.exception;

/**
 * Класс для обработки исключения для когда аргумент равен null или пустой
 */
public class IncorrectArgumentException extends Exception {
    public IncorrectArgumentException(String message) {
        super(message);
    }
}


