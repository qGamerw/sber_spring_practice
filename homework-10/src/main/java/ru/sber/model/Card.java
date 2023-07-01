package ru.sber.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Класс для описания карты клиента
 */
@Data
@AllArgsConstructor
public class Card {
    private long idCard;
    private BigDecimal account;
}
