package ru.sber.services;

import ru.sber.entities.Payment;

/**
 * Сервис для оплаты товаров
 */
public interface PaymentService {

    /**
     * Совершает платеж
     *
     * @param payment платеж
     * @return boolean
     */
    boolean pay(Payment payment);

}
