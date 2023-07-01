package ru.sber.services;

import ru.sber.entity.PromoCode;

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
    boolean isPromoCode(long id);
}
