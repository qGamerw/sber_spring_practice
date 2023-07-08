package ru.sber.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Продукт
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @NotEmpty(message = "Имя не заполнено")
    private String name;

    @Column(nullable = false)
    @NotNull(message = "Цена не заполнена")
    private BigDecimal price;

    @Column(nullable = false)
    @NotNull(message = "Количество не заполнено")
    private Integer amount;
}
