package ru.sber.services;

import ru.sber.model.PaymentDetails;

import java.math.BigDecimal;

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
    BigDecimal pay(PaymentDetails paymentDetails);
}
