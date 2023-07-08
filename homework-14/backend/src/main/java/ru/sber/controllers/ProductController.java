package ru.sber.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.sber.entities.Product;
import ru.sber.services.ProductService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * Контроллер для взаимодействия с продуктом
 */
@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Long> addProduct(@Valid @RequestBody Product product) {
        log.info("Добавляет товар {}", product);

        long productId = productService.addProduct(product);
        return ResponseEntity
                .created(URI.create("products/" + productId))
                .build();
    }

    @GetMapping
    public List<Product> getListProducts(@RequestParam(required = false) String name) {
        log.info("Получает товары по имени {}", name);

        if (name == null) {
            name = "";
        }

        return productService.findAllByName(name);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getListProducts(@PathVariable long id) {
        log.info("Получает товар по id {}", id);

        Optional<Product> product = productService.findById(id);

        if (product.isPresent()) {
            return ResponseEntity
                    .ok()
                    .body(product.get());
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody Product product) {
        log.info("Обновляет информацию о товаре {}", product);

        productService.update(product);
        return ResponseEntity
                .ok()
                .body(product);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id) {
        log.info("Удаляет продукт с id");

        boolean isDeleted = productService.deleteById(id);

        if (isDeleted) {
            return ResponseEntity
                    .noContent()
                    .build();
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }
}
