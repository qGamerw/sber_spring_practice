package ru.sber.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Класс для описания оплаты
 */
@Data
public class PaymentDetails {
    private long idClient;
    private BigDecimal sum;
}

