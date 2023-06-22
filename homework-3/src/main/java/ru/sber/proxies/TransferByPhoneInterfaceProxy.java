package ru.sber.proxies;

import ru.sber.exception.TransferByPhoneException;

import java.math.BigDecimal;

/**
 * Интерфейс класса для перевода денежных средств по номеру телефона
 */
public interface TransferByPhoneInterfaceProxy {
    void transferByPhone(String phone, BigDecimal sum) throws TransferByPhoneException;
}
