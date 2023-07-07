package ru.sber.services;

import ru.sber.entity.Product;

import java.util.List;

/**
 * Сервис для взаимодействия с корзиной пользователя
 */
public interface BasketServiceInterface {
    /**
     * Добавление товара в корзину
     *
     * @param userId    id пользователя
     * @param productId id товара
     * @param amount    Количество товара
     * @return boolean
     */
    boolean addToCart(long userId, long productId, int amount);

    /**
     * Изменение количества товара в корзине
     *
     * @param userId    id пользователя
     * @param productId id товара
     * @param amount    Количество товара
     * @return boolean
     */
    boolean updateProductAmount(long userId, long productId, int amount);

    /**
     * Удаление товара из корзины
     *
     * @param userId    id пользователя
     * @param productId id товара
     * @return boolean
     */
    boolean deleteProduct(long userId, long productId);

    /**
     * Полностью очищает корзину пользователя
     *
     * @param userId id пользователя
     */
    void clearCart(long userId);

    /**
     * Выдает список товаров в корзине пользователя
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
