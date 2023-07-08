package ru.sber.services;

import ru.sber.entities.Product;

import java.util.List;

/**
 * Сервис для взаимодействия с корзиной пользователя
 */
public interface CartService {
    /**
     * Добавляет товар в корзину
     *
     * @param userId    id пользователя
     * @param productId id товара
     * @param amount    количество товара
     * @return boolean
     */
    boolean addToCart(long userId, long productId, int amount);

    /**
     * Изменяет количество товара в корзине
     *
     * @param userId    id пользователя
     * @param productId id товара
     * @param amount    количество товара
     * @return boolean
     */
    boolean updateProductAmount(long userId, long productId, int amount);

    /**
     * Удаляет товар из корзины
     *
     * @param userId    id пользователя
     * @param productId id товара
     * @return boolean
     */
    boolean deleteProduct(long userId, long productId);

    /**
     * Очищает внешние ссылки пользователя с корзиной
     *
     * @param userId id пользователя
     */
    void clearCart(long userId);

    /**
     * Выводит список товаров в корзине
     *
     * @param userId id пользователя
     * @return List<Product>
     */
    List<Product> getListOfProductsInCart(long userId);

    /**
     * Подсчитывает количество товаров в корзине пользователя
     *
     * @param userId id пользователя
     * @return int
     */
    int countProductsInCart(long userId);

}
