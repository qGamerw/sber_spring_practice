package ru.sber.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение если пользователь пытается оплатить пустую корзину
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Корзина пуста")
public class CartIsEmptyException extends RuntimeException {
}
