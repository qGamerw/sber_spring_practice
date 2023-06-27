package ru.sber.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Basket {
    private long id;
    private List<Product> productList;
    private int promoCode;
}
