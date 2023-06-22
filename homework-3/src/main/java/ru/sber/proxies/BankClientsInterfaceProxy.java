package ru.sber.proxies;

import ru.sber.model.Client;

/**
 * Интерфейс класса для проверки списка клиентов банка
 */
public interface BankClientsInterfaceProxy {
    boolean isBankClient(Client client);
}
