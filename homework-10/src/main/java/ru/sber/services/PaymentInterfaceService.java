package ru.sber.services;


import ru.sber.entity.Client;

import java.math.BigDecimal;

/**
 * Интерфейс для оплаты товаров в корзине
 */
public interface PaymentInterfaceService {
    /**
     * Оплачивает товар
     *
     * @param client данные платежа
     * @return BigDecimal
     */
    BigDecimal pay(Client client);
}
