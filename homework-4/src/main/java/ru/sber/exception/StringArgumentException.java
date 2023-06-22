package ru.sber.exception;

/**
 * Класс для обработки исключения для строки в аргументе
 */
public class StringArgumentException extends RuntimeException {
    public StringArgumentException(String message) {
        super(message);
    }
}
