package ru.sber.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.entity.Product;
import ru.sber.services.ProductInterfaceService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("products")
public class ProductController {
    private final ProductInterfaceService productInterfaceService;

    public ProductController(ProductInterfaceService productInterfaceService) {
        this.productInterfaceService = productInterfaceService;
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        log.info("Добавление продукта: {}", product);

        return ResponseEntity.created(URI.create("product/" + productInterfaceService.addProduct(product))).build();
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProduct(@RequestParam(required = false) String name) {
        log.info("Получение продуктов с именем {}", name);

        var product = productInterfaceService.getListProductName(name);
        if (product.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(product);
        }
    }

    @GetMapping("/")
    public ResponseEntity<Optional<Product>> getProductById(@RequestParam long id) {
        log.info("Получение списка продуктов");

        var product = productInterfaceService.getProductById(id);
        if (product.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(product);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody Product product) {
        log.info("Обновление продукта {}", product);

        var isUpdate = productInterfaceService.update(product);
        if (isUpdate) {
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProduct(@RequestParam long id) {
        log.info("Удаление продукта {}", id);

        var isDeleted = productInterfaceService.delete(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
