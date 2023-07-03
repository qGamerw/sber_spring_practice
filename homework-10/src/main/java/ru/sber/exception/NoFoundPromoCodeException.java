package ru.sber.exception;

/**
 * Исключение на ненахождение промокода
 */
public class NoFoundPromoCodeException extends RuntimeException {
    public NoFoundPromoCodeException(String message) {
        super(message);
    }
}
