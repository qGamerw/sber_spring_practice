package ru.sber.proxies;

import java.math.BigDecimal;

/**
 * Интерфейс для взаимодействия с классом для оплаты товара
 */
public interface TransferInterfaceProxy {
    /**
     * Оплачивает товар
     *
     * @param card индикатор карты
     * @param sum  BigDecimal
     */
    BigDecimal transferToPay(BigDecimal sum, long card);
}
