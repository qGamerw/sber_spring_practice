package ru.sber.repository;

import java.math.BigDecimal;
import java.util.Optional;

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
     * @return boolean
     */
    boolean add(long idClient, long idProduct, int count);

    /**
     * Редактирует количество товара
     *
     * @param idCart    индикатор клиента
     * @param idProduct индикатор товара
     * @param count     количество
     * @return boolean
     */
    boolean update(long idCart, long idProduct, int count);

    /**
     * Удаляет товар
     *
     * @param idClient  индикатор клиента
     * @param idProduct индикатор товара
     * @return boolean
     */
    boolean delete(long idClient, long idProduct);

    /**
     * Получает сумму для оплаты
     *
     * @param idClient индикатор клиента
     * @return BigDecimal
     */
    BigDecimal getPrice(long idClient);

    /**
     * Проверяет существуют ли продукты в корзине
     *
     * @param idClient индикатор клиента
     * @return результат
     */
    boolean isBasket(long idClient);

    /**
     * Проверяет хватает ли товара на складе
     *
     * @param idClient индикатор клиента
     * @return boolean
     */
    boolean isCountProduct(long idClient);

    /**
     * Удаляет продукты в корзине и на складе
     *
     * @param idClient индикатор клиента
     * @return boolean
     */
    boolean removeProductBasket(long idClient);

    /**
     * Получает id карты клиента
     *
     * @param idClient индикатор клиента
     * @return Optional<Long>
     */
    Optional<Long> getIdCard(long idClient);
}
