package ru.sber.services;

import ru.sber.entity.Client;
import ru.sber.entity.Product;
import ru.sber.entity.ProductBasket;

import java.math.BigDecimal;
import java.util.List;

/**
 * Интерфейс для взаимодействия с корзиной
 */
public interface BasketInterfaceService {
    /**
     * @param idClient id клиента
     * @param product  продукт
     * @return boolean
     */
    boolean add(long idClient, Product product);

    /**
     * @param idClient id клиента
     * @param product  продукт
     * @return boolean
     */
    boolean updateProduct(long idClient, Product product);

    /**
     * @param idClient id клиента
     * @param product  продукт
     * @return boolean
     */

    boolean deleteProduct(long idClient, Product product);

    boolean isBasket(long client);

    boolean isCountProduct(Client client);

    BigDecimal getPrice(long id);

    long getIdCard(long id);

    boolean removeProductBasket(long id);
}
