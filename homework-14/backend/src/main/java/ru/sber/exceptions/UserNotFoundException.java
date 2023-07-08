package ru.sber.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение если пользователь не найден
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Пользователя не существует")
public class UserNotFoundException extends RuntimeException {
}
