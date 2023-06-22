package ru.sber.exception;

/**
 * Класс для обработки исключения коллекции
 */
public class CollectionArgumentException extends Exception {
    public CollectionArgumentException(String message) {
        super(message);
    }
}
