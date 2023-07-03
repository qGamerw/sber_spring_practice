package ru.sber.services;

import ru.sber.entity.Client;
import ru.sber.entity.Product;
import ru.sber.model.LimitedProduct;

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
     * Обновляет количество продуктов в корзине
     *
     * @param idClient id клиента
     * @param product  продукт
     * @return boolean
     */
    boolean updateProduct(long idClient, Product product);

    /**
     * Удаляет продут в корзине
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
     * Удаляет товары в корзине при оплате
     *
     * @param id id клиента
     * @return boolean
     */
    boolean basketCleaning(long id);

    /**
     * Получает список неповторяющихся продуктов у клиента
     *
     * @param id id клиента
     * @return List<Product>
     */
    List<LimitedProduct> getClientProductListById(long id);

    /**
     * Удаляет корзину клиента
     *
     * @param id id клиента
     * @return boolean
     */
    boolean deleteBasket(long id);
}
