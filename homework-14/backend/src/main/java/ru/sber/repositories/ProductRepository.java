package ru.sber.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.entities.Product;

import java.util.List;

/**
 * Репозиторий с продуктами
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Ищет товары по подстроке названия
     *
     * @param name Строка, по которой идет поиск
     * @return List<Product>
     */
    List<Product> findAllByNameContainingIgnoreCase(String name);
}
