package ru.sber.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение когда есть ссылки на товар
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Товар ещё находится в корзине одного из пользователей")
public class ProductStillUseException extends RuntimeException {
}
