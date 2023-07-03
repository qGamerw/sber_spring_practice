package ru.sber.model;

import lombok.Data;

/**
 * Класс для нахождения промокода в диапазоне
 */
@Data
public class RangePromoCode {
    private double minDiscount;
    private double maxDiscount;
}
