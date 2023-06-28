package ru.sber.exception;

/**
 * Исключение на недостаточно средств
 */
public class NotEnoughMoneyException extends RuntimeException {
    public NotEnoughMoneyException(String message) {
        super(message);
    }
}
