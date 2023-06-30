package ru.sber.model;

import lombok.Data;

/**
 * Класс для получения из json клиента
 */
@Data
public class GetJsonClient {
    private long id;
    private String name;
    private String email;
    private long card;
    private String login;
    private String password;
    private Basket basket;
    private long promo_code;
}