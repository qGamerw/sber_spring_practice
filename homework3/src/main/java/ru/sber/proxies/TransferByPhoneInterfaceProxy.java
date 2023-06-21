package ru.sber.proxies;

import ru.sber.exception.TransferByPhoneException;

import java.math.BigDecimal;

public interface TransferByPhoneInterfaceProxy {
    void TransferByPhone(String phone, BigDecimal sum) throws TransferByPhoneException;
}
