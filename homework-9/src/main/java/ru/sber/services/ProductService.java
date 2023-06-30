package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.model.Product;
import ru.sber.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

/**
 * Класс для взаимодействия с продуктом
 */
@Slf4j
@Service
public class ProductService implements ProductInterfaceService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public long addProduct(Product product) {
        log.info("ProductService добавление продута {}", product);

        return productRepository.add(product);
    }

    @Override
    public List<Product> getListProductName(String name) {
        log.info("ProductService получение продуктов по имени {}", name);

        return productRepository.getListProductName(name);
    }

    @Override
    public Optional<Product> getProductById(long id) {
        log.info("ProductService получение продукта по id {}", id);

        return productRepository.getProductById(id);
    }

    @Override
    public boolean update(Product product) {
        log.info("ProductService обновление информации у продукта по id {}", product);

        return productRepository.update(product);
    }

    @Override
    public boolean delete(long id) {
        log.info("ProductService удаление продукта по id {}", id);

        return productRepository.delete(id);
    }
}
