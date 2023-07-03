package ru.sber.services;

import ru.sber.entity.PromoCode;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для взаимодействия с промокодом
 */
public interface PromoCodeInterfaceService {
    /**
     * Добавляет промокод
     *
     * @param promoCode промокод
     * @return long
     */
    long addPromoCode(PromoCode promoCode);

    /**
     * Получает промокод
     *
     * @param id id промокода
     * @return Optional<PromoCode>
     */
    Optional<PromoCode> getPromoCodeById(long id);

    /**
     * Обновляет промокод
     *
     * @param promoCode промокод
     * @return boolean
     */
    boolean update(PromoCode promoCode);

    /**
     * Удаляет промокод
     *
     * @param id id промокода
     * @return boolean
     */
    boolean deletePromoCodeById(long id);

    /**
     * Проверяет есть ли промокод
     *
     * @param id id промокода
     * @return boolean
     */
    boolean isPromoCodeById(long id);

    /**
     * Выводит промокоды в заданно диапазоне включительно
     *
     * @param minDiscount начальное значение
     * @param maxDiscount конечное значение
     * @return List<PromoCode>
     */
    List<PromoCode> getPromoCodeByDiscountRange(double minDiscount, double maxDiscount);
}
