package ru.sber.exception;

/**
 * Класс для обработки исключения несуществующих клиентов
 */
public class BankClientException extends RuntimeException {
    public BankClientException(String massage) {
        super(massage);
    }
}
