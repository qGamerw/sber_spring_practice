package ru.sber.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.model.Product;
import ru.sber.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

/**
 * Получает запросы для взаимодействия с продуктом
 */
@Slf4j
@RestController
@RequestMapping("products")
public class ProductController {
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping
    public long addProduct(@RequestBody Product product) {
        log.info("Добавление продукта: {}", product);
        return productRepository.add(product);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Product>> getProduct(@PathVariable String name) {
        log.info("Получение продуктов с именем {}", name);
        var product = productRepository.getListProductName(name);
        if (product.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(product);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getProduct() {
        log.info("Получение списка продуктов");
        var product = productRepository.getListProduct();
        if (product.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(product);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Optional<Product>> getProductById(@PathVariable long id) {
        log.info("Получение списка продуктов");
        var product = productRepository.getProductById(id);
        if (product.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(product);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody Product product) {
        log.info("Обновление продукта {}", product);
        boolean isUpdate = productRepository.update(product);
        if (isUpdate) {
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id) {
        log.info("Удаление продукта {}", id);
        boolean isDeleted = productRepository.delete(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
