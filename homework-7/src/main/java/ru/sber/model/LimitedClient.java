package ru.sber.model;

import lombok.Data;

import java.util.Optional;

@Data
public class LimitedClient {
    private String name;
    private String email;
    private Basket basket;

    public LimitedClient(Optional<Client> client) {
        if (client.isPresent()) {
            var c = client.get();
            this.name = c.getName();
            this.email = c.getEmail();
            this.basket = c.getBasket();
        }
    }
}
