package ru.sber.entity;

import lombok.Data;

import java.util.List;

/**
 * Пользователь с ограниченным количеством полей
 */
@Data
public class AbridgedUser {
    private long id;
    private String name;
    private String email;
    private List<Product> cart;

    public AbridgedUser(User user) {
        this.id = user.getId();
        this.name = user.getUsername();
        this.email = user.getEmail();
    }
}
