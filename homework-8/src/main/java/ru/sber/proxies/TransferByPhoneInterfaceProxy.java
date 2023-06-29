package ru.sber.proxies;

import java.math.BigDecimal;

/**
 * Интерфейс для взаимодействия с классом для оплаты товара
 */
public interface TransferByPhoneInterfaceProxy {
    /**
     * Оплачивает товар
     *
     * @param card индикатор карты
     * @param sum    сумма платежа
     */
    boolean transferToPay(BigDecimal sum, long card);
}
