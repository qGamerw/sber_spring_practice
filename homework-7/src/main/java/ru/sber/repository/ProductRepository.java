package ru.sber.repository;

import ru.sber.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    long add(Product product);

    Optional<Product> getProductById(long id);

    boolean update(Product product);

    boolean delete(long id);

    List<Product> getListProductName(String name);
    List<Product> getListProduct();
}
