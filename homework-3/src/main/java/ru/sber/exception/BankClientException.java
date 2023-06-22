package ru.sber.exception;

/**
 * Класс для обработки исключения класса BankClientAppProxy
 */
public class BankClientException extends Exception {
    public BankClientException(String massage) {
        super(massage);
    }
}
