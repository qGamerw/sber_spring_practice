package ru.sber.exception;

/**
 * Исключение на недостаточное количество товара
 */
public class InsufficientQuantityException extends RuntimeException{
    public InsufficientQuantityException(String message) {
        super(message);
    }
}
