package ru.sber.proxies;

import java.math.BigDecimal;

/**
 * Интерфейс для взаимодействия класса для проверки списка клиентов банка
 */
public interface BankUsersInterfaceProxy {
    /**
     * Проверяет являться ли пользователь клиентом банка
     *
     * @param card номер карты
     * @return boolean
     */
    boolean isBankUser(long card);

    /**
     * Получает сумму на карте
     *
     * @param card номер карты
     * @return BigDecimal
     */
    BigDecimal getCashByIdUser(long card);
}
