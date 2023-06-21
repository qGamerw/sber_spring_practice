package ru.sber.proxies;

import org.springframework.stereotype.Component;
import ru.sber.model.Client;

import java.util.List;

@Component
public class BankClientsAppProxy implements BankClientsInterfaceProxy {
    @Override
    public boolean getBankClient(Client client) {
        System.out.println("Получение списка клиентов банка");

        return List.of(1, 2, 3, 4, 5).contains(client.getId());
    }
}
