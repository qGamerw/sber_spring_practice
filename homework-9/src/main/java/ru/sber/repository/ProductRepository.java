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
     * @return long
     */
    long add(Product product);

    /**
     * Выводи продукт
     *
     * @param id индикатор продукта
     * @return Optional<Product>
     */
    Optional<Product> getProductById(long id);

    /**
     * Изменяет продукт
     *
     * @param product продукт
     * @return boolean
     */
    boolean update(Product product);

    /**
     * Удаляет продукт
     *
     * @param id индикатор продукта
     * @return boolean
     */
    boolean delete(long id);

    /**
     * Выводи список продуктов по имени
     *
     * @param name название продукта
     * @return List<Product>
     */
    List<Product> getListProductName(String name);
}
