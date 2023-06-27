package ru.sber.repository;

import org.springframework.stereotype.Repository;
import ru.sber.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Repository
public class LocalProductRepository implements ProductRepository {
    private List<Product> products = new ArrayList<>();

    @Override
    public long add(Product product) {
        product.setId(generateId());
        products.add(product);
        return product.getId();
    }

    @Override
    public Optional<Product> getProductById(long id) {
        return products.stream()
                .filter(product -> product.getId() == id)
                .findAny();
    }

    @Override
    public boolean update(Product product) {
        for (Product item : products) {
            if (item.getId() == product.getId()) {
                item.setName(product.getName());
                item.setPrice(product.getPrice());
                item.setAmount(product.getAmount());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        return products.removeIf(product -> product.getId() == id);
    }

    @Override
    public List<Product> getListProductName(String name) {
        List<Product> listProduct = new ArrayList<>();
        for (Product item : products) {
            if (name.equals(item.getName())) {
                listProduct.add(item);
            }
        }
        return listProduct;
    }

    @Override
    public List<Product> getListProduct() {
        return products;
    }

    private long generateId() {
        Random random = new Random();
        int low = 1;
        int high = 1_000_000;
        return random.nextLong(high - low) + low;
    }
}
