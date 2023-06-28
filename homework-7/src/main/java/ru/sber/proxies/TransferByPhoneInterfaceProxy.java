package ru.sber.proxies;

import java.math.BigDecimal;

/**
 * Интерфейс для взаимодействия с классом для оплаты товара
 */
public interface TransferByPhoneInterfaceProxy {
    /**
     * Оплачивает товар
     *
     * @param idCard индикатор карты
     * @param sum    сумма платежа
     */
    boolean transferToPay(long idCard, BigDecimal sum);
}
