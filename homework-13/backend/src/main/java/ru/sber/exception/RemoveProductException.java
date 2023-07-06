package ru.sber.exception;

/**
 * Исключение на ошибку удаления товара из корзины
 */
public class RemoveProductException extends RuntimeException {
    public RemoveProductException(String message) {
        super(message);
    }
}
