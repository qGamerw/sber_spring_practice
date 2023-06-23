package ru.sber.exception;

/**
 * Класс для обработки исключения null объекта в аргументе
 */
public class NullArgumentException extends RuntimeException {
    public NullArgumentException(String message) {
        super(message);
    }
}


