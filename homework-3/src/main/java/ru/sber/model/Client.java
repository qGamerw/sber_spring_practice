package ru.sber.model;

import java.math.BigDecimal;

/**
 * Класс для описания клиента
 */
public class Client {
    private final int id;
    private final String name;
    private final String phone;
    private final BigDecimal account;

    public Client(int id, String name, String phone, BigDecimal account) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public BigDecimal getAccount() {
        return account;
    }

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
