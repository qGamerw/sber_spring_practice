package ru.sber.exception;

public class EmptyBasketException extends RuntimeException{
    public EmptyBasketException(String message) {
        super(message);
    }
}
