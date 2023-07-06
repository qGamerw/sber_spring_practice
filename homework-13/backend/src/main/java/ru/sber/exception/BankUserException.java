package ru.sber.exception;

/**
 * Класс для обработки исключения несуществующих клиентов
 */
public class BankUserException extends RuntimeException {
    public BankUserException(String massage) {
        super(massage);
    }
}
