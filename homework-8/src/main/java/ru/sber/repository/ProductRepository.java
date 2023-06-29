package ru.sber.repository;

import ru.sber.model.Product;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для взаимодействия с продуктом
 */
public interface ProductRepository {
    /**
     * Добавляет продукт
     *
     * @param product продукт
     * @return индикатор продукта
     */
    long add(Product product);

    /**
     * Выводи продукт
     *
     * @param id индикатор продукта
     * @return результат
     */
    Optional<Product> getProductById(long id);

    /**
     * Изменяет продукт
     *
     * @param product продукт
     * @return результат
     */
    boolean update(Product product);

    /**
     * Удаляет продукт
     *
     * @param id индикатор продукта
     * @return результат
     */
    boolean delete(long id);

    /**
     * Выводи список продуктов по имени
     *
     * @param name название продукта
     * @return результат
     */
    List<Product> getListProductName(String name);

    /**
     * Выводи список всех продуктов
     *
     * @return результат
     */
    List<Product> getListProduct();
}
