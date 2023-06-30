package ru.sber.exception;

public class RemoveProductException extends RuntimeException{
    public RemoveProductException(String message) {
        super(message);
    }
}
