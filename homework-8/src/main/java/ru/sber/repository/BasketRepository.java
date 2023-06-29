package ru.sber.repository;

import ru.sber.model.Product;

import java.math.BigDecimal;
import java.util.List;

/**
 * Интерфейс для взаимодействия с корзиной
 */
public interface BasketRepository {
    /**
     * Добавляет товар
     *
     * @param idClient  индикатор клитента
     * @param idProduct индикатор товара
     * @param count     количество
     * @return результат
     */
    boolean add(long idClient, long idProduct, int count);

    /**
     * Редактирует количество товара
     *
     * @param idCart    индикатор клиента
     * @param idProduct индикатор товара
     * @param count     количество
     * @return результат
     */
    boolean update(long idCart, long idProduct, int count);

    /**
     * Удаляет товар
     *
     * @param idClient  индикатор клиента
     * @param idProduct индикатор товара
     * @return результат
     */
    boolean delete(long idClient, long idProduct);


    /**
     * Выводит список товаров в корзине
     *
     * @return результат
     */
    List<Product> getListBasket();

    /**
     * Генерирует индефикатор корзины
     *
     * @return результат
     */
    long generateLongId();

    /**
     * Удаление корзины по индефикатору
     *
     * @return результат
     */
    boolean deleteBasket(long idBasket);

    BigDecimal getPrice(long idClient);
}
