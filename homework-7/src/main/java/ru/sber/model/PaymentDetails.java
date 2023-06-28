package ru.sber.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentDetails {
    private long idClient;
    private BigDecimal sum;
}

