package ru.sber.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Корзина
 */
@Entity
@Data
@Table(name = "products_carts")
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

    @Column(nullable = false)
    private int amount;
}
