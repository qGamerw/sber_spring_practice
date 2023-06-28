package ru.sber.exception;

/**
 * Исключение на пустую корзину
 */
public class EmptyBasketException extends RuntimeException {
    public EmptyBasketException(String message) {
        super(message);
    }
}
