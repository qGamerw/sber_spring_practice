package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.entity.Product;
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
        log.info("ProductService добавляет продукт c id {}", product.getId());

        return productRepository.save(product).getId();
    }

    @Override
    public List<Product> getListProductsByName(String name) {
        log.info("ProductService получает товар по имени {}", name);

        if (name == null) {
            return productRepository.findAll();
        }

        return productRepository.findByNameIsLikeIgnoreCase(name);
    }

    @Override
    public Optional<Product> getProductById(long id) {
        log.info("ProductService получает товар по id {}", id);

        return productRepository.findById(id);
    }

    @Override
    public boolean update(Product product) {
        log.info("ProductService обновляет товар по id {}", product.getId());

        var isProduct = productRepository.existsById(product.getId());

        if (isProduct) {
            productRepository.save(product);
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(long id) {
        log.info("ProductService удаляет товар по id {}", id);

        var isProduct = productRepository.existsById(id);

        if (isProduct) {
            productRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
