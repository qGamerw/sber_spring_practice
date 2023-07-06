package ru.sber.services;

import ru.sber.entity.Product;
import ru.sber.model.LimitedProduct;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для реализации взаимодействия с продуктом
 */
public interface ProductInterfaceService {
    /**
     * Добавляет продукт
     *
     * @param product продукт
     * @return long
     */
    long addProduct(Product product);

    /**
     * Выводит продукты по имени
     *
     * @param name имя продукта
     * @return List<Product>
     */
    List<LimitedProduct> getListProductsByName(String name);

    /**
     * Выводит продут по id
     *
     * @param id id продукта
     * @return Optional<Product>
     */

    Optional<LimitedProduct> getProductById(long id);

    /**
     * Обновляет информацию о продукте
     *
     * @param product продукт
     * @return boolean
     */
    boolean update(Product product);

    /**
     * Удаляет продукт
     *
     * @param id id продукта
     * @return boolean
     */
    boolean delete(long id);
}
