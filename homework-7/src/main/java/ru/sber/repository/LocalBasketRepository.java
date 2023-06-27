package ru.sber.repository;

import org.springframework.stereotype.Repository;
import ru.sber.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Repository
public class LocalBasketRepository implements BasketRepository {

    private final ProductRepository productRepository;
    private List<Product> listBasket;

    public LocalBasketRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.listBasket = new ArrayList<>();
    }



    @Override
    public List<Product> add(long id, int count) {
        var productOptional = productRepository.getProductById(id);
        if (productOptional.isPresent()) {
            var product = productOptional.get();
            product.setAmount(count);
            listBasket.add(product);
        }
        return listBasket;
    }

    @Override
    public List<Product> update(long id, int count) {
        var productOptional = productRepository.getProductById(id);
        if (productOptional.isPresent()) {
            for (Product item : listBasket) {
                if (item.getId() == id) {
                    item.setAmount(count);
                    break;
                }
            }
        }
        return listBasket;
    }

    @Override
    public List<Product> delete(long id) {
        var productOptional = productRepository.getProductById(id);
        if (productOptional.isPresent()) {
            listBasket.removeIf(item -> item.getId() == id);
        }
        return listBasket;
    }

    @Override
    public List<Product> getListBasket() {
        return listBasket;
    }

    @Override
    public long generateLongId() {
        Random random = new Random();
        int low = 1;
        int high = 1_000_000;
        return random.nextLong(high - low) + low;
    }
}
