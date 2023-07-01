package ru.sber.services;

import ru.sber.entity.Client;
import ru.sber.entity.Product;

import java.math.BigDecimal;
import java.util.List;

/**
 * Интерфейс для взаимодействия с корзиной
 */
public interface BasketInterfaceService {

    /**
     * Добавляет продукт в корзину
     *
     * @param idClient id клиента
     * @param product  продукт
     * @return boolean
     */
    boolean add(long idClient, Product product);

    /**
     * Обновляет количество продутов в корзине
     *
     * @param idClient id клиента
     * @param product  продукт
     * @return boolean
     */
    boolean updateProduct(long idClient, Product product);

    /**
     * Удаляет продуты в корзине
     *
     * @param idClient id клиента
     * @param product  продукт
     * @return boolean
     */
    boolean deleteProduct(long idClient, Product product);

    /**
     * Проверяет если ли товары в корзине
     *
     * @param client id клиента
     * @return boolean
     */
    boolean isBasket(long client);

    /**
     * Проверяет достаточно ли количество товара на складе
     *
     * @param client id клиента
     * @return boolean
     */
    boolean isCountProduct(Client client);

    /**
     * Получает сумму к оплате
     *
     * @param idClient    id клиента
     * @param idPromoCode id промокода
     * @return boolean
     */
    BigDecimal getPrice(long idClient, long idPromoCode);

    /**
     * Получает карту клиента
     *
     * @param id id клиента
     * @return boolean
     */
    long getIdCard(long id);

    /**
     * Удаляет товары в корзине
     *
     * @param id id клиента
     * @return boolean
     */
    boolean removeProductBasket(long id);

    /**
     * Получает список продуктов у клиента
     *
     * @param id id клиента
     * @return List<Product>
     */
    List<Product> getClientById(long id);
}
