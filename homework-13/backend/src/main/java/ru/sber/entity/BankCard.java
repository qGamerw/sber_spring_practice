package ru.sber.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Заглушка к сервису оплаты
 */
@Data
@AllArgsConstructor
public class BankCard {
    private long numberOfCard;
    private BigDecimal money;
}
