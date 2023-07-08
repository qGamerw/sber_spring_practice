package ru.sber.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение если банковской карты не существует
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Не существует карты")
public class CardIsNotExistException extends RuntimeException {
}
