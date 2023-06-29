package ru.sber.proxies;

import java.math.BigDecimal;

/**
 * Интерфейс для взаимодействия класса для проверки списка клиентов банка
 */
public interface BankClientsInterfaceProxy {
    /**
     * Проверяет являться ли пользователь клиентом банка
     *
     * @param card номер карты для проверки
     * @return результат проверки
     */
    boolean isBankClient(long card);

    /**
     * Получает сумму на карте
     *
     * @param card номер карты для проверки
     * @return сумма
     */
    BigDecimal getCashByIdClient(long card);
}
