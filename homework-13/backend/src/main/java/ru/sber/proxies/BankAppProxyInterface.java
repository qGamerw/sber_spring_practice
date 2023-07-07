package ru.sber.proxies;

import java.math.BigDecimal;

public interface BankAppProxyInterface {

    /**
     * Показывает баланс денег на карте
     *
     * @param numberOfCard Номер банковской карты
     * @return результат
     */
    BigDecimal getAmountOfMoneyInTheAccount(long numberOfCard);
}
