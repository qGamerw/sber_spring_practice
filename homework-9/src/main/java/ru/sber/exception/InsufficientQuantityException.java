package ru.sber.exception;

public class InsufficientQuantityException extends RuntimeException{
    public InsufficientQuantityException(String message) {
        super(message);
    }
}
