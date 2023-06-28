package ru.sber.repository;

import org.springframework.stereotype.Repository;
import ru.sber.exception.IncorrectAmountException;
import ru.sber.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Класс для взаимодействия с корзиной
 */
@Repository
public class LocalBasketRepository implements BasketRepository {

    private final ProductRepository productRepository;
    private final List<Product> listBasket;

    public LocalBasketRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.listBasket = new ArrayList<>();
    }


    @Override
    public boolean add(long id, int count) {
        var productOptional = productRepository.getProductById(id);
        if (productOptional.isPresent()) {
            if (count < 1) {
                throw new IncorrectAmountException("Некорректное значение количества");
            }
            var product = productOptional.get();
            product.setAmount(count);
            listBasket.add(product);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(long id, int count) {
        var productOptional = productRepository.getProductById(id);
        if (productOptional.isPresent()) {
            for (Product item : listBasket) {
                if (item.getId() == id) {
                    item.setAmount(count);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        var productOptional = productRepository.getProductById(id);
        return productOptional.isPresent()
                && listBasket.removeIf(item -> item.getId() == id);
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
