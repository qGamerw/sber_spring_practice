package ru.sber.proxies;

import java.math.BigDecimal;

/**
 * Интерфейс для взаимодействием с банком
 */
public interface BankAppProxyInterface {
    /**
     * Выводит баланс на карте
     *
     * @param numberOfCard Номер карты
     * @return BigDecimal
     */
    BigDecimal getAmountOfMoneyInTheAccount(long numberOfCard);

}
