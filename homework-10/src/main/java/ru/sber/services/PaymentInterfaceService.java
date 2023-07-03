package ru.sber.services;

import ru.sber.model.PaymentDetails;

/**
 * Интерфейс для оплаты товаров в корзине
 */
public interface PaymentInterfaceService {
    /**
     * Оплачивает товар
     *
     * @param paymentDetails данные платежа
     * @return BigDecimal
     */
    boolean pay(PaymentDetails paymentDetails);
}
