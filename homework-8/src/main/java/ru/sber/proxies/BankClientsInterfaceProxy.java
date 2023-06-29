package ru.sber.proxies;

import ru.sber.model.Card;

import java.math.BigDecimal;
import java.util.List;

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

    /**
     * Выводит карты банка
     *
     * @return результат проверки
     */
    List<Card> getCards();
}
