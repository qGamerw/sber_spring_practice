package ru.sber.services;

import ru.sber.entity.Payment;

/**
 * Сервис для оплаты товаров
 */
public interface PaymentServiceInterface {

    /**
     * Оплачивает товар
     *
     * @param payment Данные оплаты
     * @return boolean
     */
    boolean pay(Payment payment);
}
