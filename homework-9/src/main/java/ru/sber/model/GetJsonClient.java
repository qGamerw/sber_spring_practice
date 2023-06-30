package ru.sber.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Класс для получение json клиента
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