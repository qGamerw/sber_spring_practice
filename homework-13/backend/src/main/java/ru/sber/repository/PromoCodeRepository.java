package ru.sber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.entity.PromoCode;

import java.util.List;

/**
 * Интерфейс для взаимодействия с промокодом
 */
@Repository
public interface PromoCodeRepository extends JpaRepository<PromoCode, Long> {
    /**
     * Проверяет есть ли такой промокод
     *
     * @param id id промокода
     * @return boolean
     */
    boolean existsById(long id);

    /**
     * Выводит промокоды в заданном диапазоне включительно
     *
     * @param minDiscount начальное значение скидки
     * @param maxDiscount конечное значение скидки
     * @return List<PromoCode>
     */
    List<PromoCode> findByDiscountBetween(double minDiscount, double maxDiscount);
}
