package ru.sber.model;

import lombok.Data;

/**
 * Класс для отправки ошибки на запрос
 */
@Data
public class ErrorDetails {
    private String message;
}
