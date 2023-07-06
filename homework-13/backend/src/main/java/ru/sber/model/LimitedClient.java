package ru.sber.model;

import lombok.Data;
import ru.sber.entity.Client;
import ru.sber.entity.Product;

import java.math.BigDecimal;
import java.util.List;

/**
 * Класс для описания укороченного клиента
 */
@Data
public class LimitedClient {
    private String name;
    private String email;
    private long idCard;
    private List<LimitedProduct> products;
    private BigDecimal price;

    public LimitedClient(Client client, List<LimitedProduct> products) {

        this.name = client.getName();
        this.email = client.getEmail();
        this.products = products;
        this.idCard = client.getIdCard();
        this.price = client.getPrice();
    }
}
