package ru.sber.exception;

/**
 * Исключение на некорректное значение
 */
public class IncorrectAmountException extends RuntimeException {
    public IncorrectAmountException(String message) {
        super(message);
    }
}
