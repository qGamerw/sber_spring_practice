package ru.sber.repository;

import ru.sber.model.Product;

import java.util.List;

/**
 * Интерфейс для взаимодействия с корзиной
 */
public interface BasketRepository {
    /**
     * Добавляет товар
     *
     * @param id    индикатор товара
     * @param count количество
     * @return результат
     */
    boolean add(long id, int count);

    /**
     * Редактирует количество товара
     *
     * @param id    индикатор товара
     * @param count количество
     * @return результат
     */
    boolean update(long id, int count);

    /**
     * Удаляет товар
     *
     * @param id индикатор товара
     * @return результат
     */
    boolean delete(long id);

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
}
