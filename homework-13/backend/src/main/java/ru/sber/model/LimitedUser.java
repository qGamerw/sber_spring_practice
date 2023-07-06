package ru.sber.model;

import lombok.Data;
import ru.sber.entity.User;

import java.math.BigDecimal;
import java.util.List;

/**
 * Класс для описания укороченного клиента
 */
@Data
public class LimitedUser {
    private String name;
    private String email;
    private long idCard;
    private List<LimitedProduct> products;
    private BigDecimal price;

    public LimitedUser(User user, List<LimitedProduct> products) {

        this.name = user.getName();
        this.email = user.getEmail();
        this.products = products;
        this.idCard = user.getIdCard();
        this.price = user.getPrice();
    }
}
