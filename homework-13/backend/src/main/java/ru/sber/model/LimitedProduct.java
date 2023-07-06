package ru.sber.model;

import lombok.Data;
import ru.sber.entity.Product;
import ru.sber.entity.ProductBasket;

import java.math.BigDecimal;

/**
 * Класс для получения обрезанного клиента
 */
@Data
public class LimitedProduct {
    private long id;
    private String name;
    private BigDecimal price;
    private long amount;
    private String url;

    public LimitedProduct(ProductBasket productBasket) {
        this.id = productBasket.getProduct().getId();
        this.name = productBasket.getProduct().getName();
        this.price = productBasket.getProduct().getPrice();
        this.amount = productBasket.getAmount();
    }

    public LimitedProduct(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.amount = product.getAmount();
        this.url = "https://w.forfun.com/fetch/b5/b59cb84829d07b33035b671f69184a61.jpeg";
    }
}
