package ru.sber.exception;

/**
 * Класс для обработки исключения для когда аргумент пустая строка
 */
public class EmptyStringArgumentException extends RuntimeException {
    public EmptyStringArgumentException(String message) {
        super(message);
    }
}
