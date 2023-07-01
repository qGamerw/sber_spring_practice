package ru.sber.model;

import lombok.Data;
import ru.sber.entity.Client;
import ru.sber.entity.Product;

import java.util.List;
import java.util.Optional;

/**
 * Класс для описания укороченного клиента
 */
@Data
public class LimitedClient {
    private String name;
    private String email;
    private long idCard;
    private List<Product> products;

    public LimitedClient(Optional<Client> client, List<Product> products) {
        if (client.isPresent()) {
            this.name = client.get().getName();
            this.email = client.get().getEmail();
            this.products = products;
            this.idCard = client.get().getIdCard();
        }
    }
}
