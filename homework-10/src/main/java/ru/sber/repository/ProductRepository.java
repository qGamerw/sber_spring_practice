package ru.sber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.entity.Product;

/**
 * Интерфейс для взаимодействия с продуктами
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
