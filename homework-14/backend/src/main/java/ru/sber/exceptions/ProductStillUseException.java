package ru.sber.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение если есть ссылки на продукт
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Товар ещё имеет сторонние ссылки")
public class ProductStillUseException extends RuntimeException {
}
