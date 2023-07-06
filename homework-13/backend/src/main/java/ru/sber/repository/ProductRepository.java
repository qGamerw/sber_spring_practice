package ru.sber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.entity.Product;

import java.util.List;

/**
 * Интерфейс для взаимодействия с продуктами
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    /**
     * Выводит продукт по имени
     *
     * @param name продукта
     * @return List<Product>
     */
    List<Product> findByNameIsLikeIgnoreCase(String name);

    /**
     * Проверяет есть ли продукт
     *
     * @param id id продукта
     * @return boolean
     */
    boolean existsByName(String id);
}
