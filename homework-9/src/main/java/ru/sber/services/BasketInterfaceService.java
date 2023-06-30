package ru.sber.services;

import ru.sber.model.Product;

/**
 * Интерфейс для взаимодействия с корзиной
 */
public interface BasketInterfaceService {
    /**
     * @param idClient id клиента
     * @param product  продукт
     * @return boolean
     */
    boolean addProduct(long idClient, Product product);

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
}
