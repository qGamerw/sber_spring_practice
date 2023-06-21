package ru.sber.exception;

public class BankClientException extends Exception {
    public BankClientException(String massage) {
        super(massage);
    }
}
