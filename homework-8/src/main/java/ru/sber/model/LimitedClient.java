package ru.sber.model;

import lombok.Data;

import java.util.Optional;

/**
 * Класс для описания укороченного клиента
 */
@Data
public class LimitedClient {
    private String name;
    private String email;
    private long idCard;
    private Basket basket;

    public LimitedClient(Optional<Client> client) {
        if (client.isPresent()) {
            this.name = client.get().getName();
            this.email = client.get().getEmail();
            this.basket = client.get().getBasket();
            this.idCard = client.get().getCard();
        }
    }
}
