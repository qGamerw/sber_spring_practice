package ru.sber.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.model.Product;
import ru.sber.repository.ProductRepository;

import java.util.List;

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
        var product = productRepository.getListProductName(name);
        if (product.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            log.info("Получение продукта с именем {}, id {}", name, product);
            return ResponseEntity.ok().body(product);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getProduct() {
        var product = productRepository.getListProduct();
        if (product.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            log.info("Получение списка продуктов {}", product);
            return ResponseEntity.ok().body(product);
        }
    }

    @PutMapping
    public Product updateProduct(@RequestBody Product product) {
        log.info("Обновление продукта {}", product);
        productRepository.update(product);
        return product;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id) {
        boolean isDeleted = productRepository.delete(id);
        if (isDeleted) {
            log.info("Удаление продукта {}", id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
