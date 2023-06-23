package ru.sber.exception;

/**
 * Класс для обработки исключения пустой строки в аргументе
 */
public class EmptyStringArgumentException extends RuntimeException {
    public EmptyStringArgumentException(String message) {
        super(message);
    }
}
