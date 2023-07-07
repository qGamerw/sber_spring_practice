package ru.sber.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Платеж
 */
@Data
@AllArgsConstructor
public class Payment {

    @NotNull(message = "Не указан id карты")
    Long cardNumber;
    @NotNull(message = "Не указан User Id")
    Long userId;
}
