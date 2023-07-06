package ru.sber.services;

import ru.sber.entity.Product;
import ru.sber.entity.User;
import ru.sber.model.LimitedProduct;

import java.util.List;

/**
 * Интерфейс для взаимодействия с корзиной
 */
public interface BasketInterfaceService {

    /**
     * Добавляет продукт в корзину
     *
     * @param idUser  id клиента
     * @param product продукт
     * @return boolean
     */
    boolean add(long idUser, Product product);

    /**
     * Обновляет количество продуктов в корзине
     *
     * @param idUser  id клиента
     * @param product продукт
     * @return boolean
     */
    boolean updateProduct(long idUser, Product product);

    /**
     * Удаляет продут в корзине
     *
     * @param idUser  id клиента
     * @param product продукт
     * @return boolean
     */
    boolean deleteProduct(long idUser, Product product);

    /**
     * Проверяет если ли товары в корзине
     *
     * @param user id клиента
     * @return boolean
     */
    boolean isBasket(long user);

    /**
     * Проверяет достаточно ли количество товара на складе
     *
     * @param user id клиента
     * @return boolean
     */
    boolean isCountProduct(User user);

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
    List<LimitedProduct> getUserProductListById(long id);

    /**
     * Удаляет корзину клиента
     *
     * @param id id клиента
     * @return boolean
     */
    boolean deleteBasket(long id);
}
