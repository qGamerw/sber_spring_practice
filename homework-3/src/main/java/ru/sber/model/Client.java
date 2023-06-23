package ru.sber.model;

import java.math.BigDecimal;

/**
 * Класс для описания клиента
 */
public record Client(int id, String name, String phone, BigDecimal account) {
    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", account=" + account +
                '}';
    }
}