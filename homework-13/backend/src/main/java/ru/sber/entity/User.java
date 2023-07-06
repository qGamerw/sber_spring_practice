package ru.sber.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    List<ProductBasket> productBaskets = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private long idCard;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))

    private Set<Role> roles = new HashSet<>();

    public User(long id, String name, String email, long idCard, String username, String password, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.idCard = idCard;
        this.username = username;
        this.password = password;
        this.price = price;
    }

    public User(String name, String email, long idCard, String username, String password) {
        this.name = name;
        this.email = email;
        this.idCard = idCard;
        this.username = username;
        this.password = password;
        this.price = BigDecimal.ZERO;
    }
}
