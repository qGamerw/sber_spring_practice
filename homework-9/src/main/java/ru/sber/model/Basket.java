package ru.sber.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Класс для описания корзины
 */
@Data
@AllArgsConstructor
public class Basket {
    private long id;
    private List<Product> productList;
    private long promoCode;
}
