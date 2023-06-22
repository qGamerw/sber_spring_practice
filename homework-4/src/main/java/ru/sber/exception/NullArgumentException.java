package ru.sber.exception;

/**
 * Класс для обработки исключения для когда аргумент равен null
 */
public class NullArgumentException extends RuntimeException {
    public NullArgumentException(String message) {
        super(message);
    }
}


