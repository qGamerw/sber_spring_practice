package ru.sber.proxies;

import ru.sber.model.Client;

/**
 * Интерфейс для взаимодействия класса для проверки списка клиентов банка
 */
public interface BankClientsInterfaceProxy {
    /**
     * Проверяет являться ли пользователь клиентом банка
     *
     * @param client клиент для проверки
     * @return результат проверки
     */
    boolean isBankClient(Client client);
}
