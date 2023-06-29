package ru.sber.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Класс для описания продукта
 */
@Data
@AllArgsConstructor
public class Product {
    private long id;
    private String name;
    private BigDecimal price;
    private int count;
}
