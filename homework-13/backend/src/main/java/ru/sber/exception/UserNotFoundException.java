package ru.sber.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение пользователь не найден
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Пользователь не существует")
public class UserNotFoundException extends RuntimeException {
}
