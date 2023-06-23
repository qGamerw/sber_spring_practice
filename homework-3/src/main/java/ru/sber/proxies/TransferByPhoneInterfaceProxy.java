package ru.sber.proxies;

import ru.sber.exception.TransferByPhoneException;

import java.math.BigDecimal;

/**
 * Интерфейс для взаимодействия с классом для перевода денежных средств по номеру телефона
 */
public interface TransferByPhoneInterfaceProxy {
    /**
     * Переводит сумму на телефон
     *
     * @param phone телефон
     * @param sum   сумма перевода
     */
    void transferByPhone(String phone, BigDecimal sum) throws TransferByPhoneException;
}
