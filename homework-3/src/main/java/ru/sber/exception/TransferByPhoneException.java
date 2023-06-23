package ru.sber.exception;

/**
 * Класс для обработки исключения отрицательной суммы перевода
 */
public class TransferByPhoneException extends Exception {
    public TransferByPhoneException(String massage) {
        super(massage);
    }
}
