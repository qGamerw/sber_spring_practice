package ru.sber.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение если товара на складе недостаточно
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Товара на складе недостаточно")
public class OutOfStockException extends RuntimeException {
}
