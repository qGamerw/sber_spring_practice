package ru.sber.model;

import lombok.Data;
import ru.sber.entity.Client;
import ru.sber.entity.Product;

import java.util.List;

/**
 * Класс для описания укороченного клиента
 */
@Data
public class LimitedClient {
    private String name;
    private String email;
    private long idCard;
    private List<Product> products;

    public LimitedClient(Client client, List<Product> products) {

        this.name = client.getName();
        this.email = client.getEmail();
        this.products = products;
        this.idCard = client.getIdCard();

    }
}
