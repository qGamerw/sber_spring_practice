package ru.sber.model;

import java.math.BigDecimal;

public class Client {
    private int id;
    private String name;
    private String phone;
    private BigDecimal account;

    public Client(int id, String name, String phone, BigDecimal sum) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.account = sum;
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
