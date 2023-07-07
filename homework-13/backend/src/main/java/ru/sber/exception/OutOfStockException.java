package ru.sber.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение товара на складе недостаточно
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Товара на складе недостаточно")
public class OutOfStockException extends RuntimeException {
}
