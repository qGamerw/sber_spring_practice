package ru.sber.proxies;

import org.springframework.stereotype.Component;
import ru.sber.model.Client;

import java.util.List;

/**
 * Класс для проверки списка клиентов банка
 */
@Component
public class BankClientsAppProxy implements BankClientsInterfaceProxy {
    @Override
    public boolean isBankClient(Client client) {
        System.out.println("Поиск пользователя в банке.");
        return List.of(1, 2, 3).contains(client.id());
    }
}
