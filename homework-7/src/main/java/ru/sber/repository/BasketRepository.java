package ru.sber.repository;

import ru.sber.model.Basket;
import ru.sber.model.Client;
import ru.sber.model.Product;

import java.util.HashMap;
import java.util.List;

public interface BasketRepository {
    List<Product> add(long id, int count);
    List<Product> update(long id, int count);
    List<Product> delete(long id);
    List<Product> getListBasket();
    long generateLongId();
}
