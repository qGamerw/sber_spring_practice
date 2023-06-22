package ru.sber.exception;

/**
 * Класс для обработки исключения пустой коллекции
 */
public class EmptyCollectionArgumentException extends RuntimeException {
    public EmptyCollectionArgumentException(String message) {
        super(message);
    }
}
