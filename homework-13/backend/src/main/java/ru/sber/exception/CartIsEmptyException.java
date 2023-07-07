package ru.sber.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение пустая корзина
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Корзина пуста")
public class CartIsEmptyException extends RuntimeException {
}
