package ru.sber.model;

import lombok.Data;

/**
 * Класс для описания оплаты
 */
@Data
public class PaymentDetails {
    private long idClient;
    private long idPromoCode;
}


