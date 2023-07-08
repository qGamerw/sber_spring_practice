package ru.sber.services;

import ru.sber.entities.Product;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для взаимодействия с товарами
 */
public interface ProductService {

    /**
     * Добавляет новый товар
     *
     * @param product новый товар
     * @return long
     */
    long addProduct(Product product);

    /**
     * Ищет товар по id
     *
     * @param id id товара
     * @return Optional<Product>
     */
    Optional<Product> findById(long id);

    /**
     * Ищет товары по названию
     *
     * @param name название товара
     * @return List<Product>
     */
    List<Product> findAllByName(String name);

    /**
     * Изменяет товар
     *
     * @param product новый товар
     * @return boolean
     */
    boolean update(Product product);

    /**
     * Проверяет существование товара
     *
     * @param productId id товара
     * @return boolean
     */
    boolean checkProductExistence(long productId);

    /**
     * Удаляет товар по id
     *
     * @param productId id товара
     * @return boolean
     */
    boolean deleteById(long productId);


}
