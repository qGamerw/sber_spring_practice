package ru.sber.exception;

/*
 * Класс для обработки исключения класса TransferByPhoneAppProxy
 */
public class TransferByPhoneException extends Exception {
    public TransferByPhoneException(String massage) {
        super(massage);
    }
}
