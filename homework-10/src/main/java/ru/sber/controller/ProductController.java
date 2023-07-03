package ru.sber.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.entity.Product;
import ru.sber.model.LimitedProduct;
import ru.sber.services.ProductInterfaceService;

import java.net.URI;
import java.net.URISyntaxException;
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
    public ResponseEntity<Void> addProduct(@RequestBody Product product) throws URISyntaxException {
        log.info("Добавление продукта {}", product);

        long id = productInterfaceService.addProduct(product);

        return ResponseEntity
                .created(new URI("http://localhost:8080/products/" + id))
                .build();
    }

    @GetMapping
    public ResponseEntity<List<LimitedProduct>> getProductByName(@RequestParam(required = false) String name) {
        log.info("Получение продуктов с именем {}", name);

        var productList = productInterfaceService.getListProductsByName(name);

        if (productList.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        } else {
            return ResponseEntity
                    .ok()
                    .body(productList);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<LimitedProduct>> getProductById(@PathVariable long id) {
        log.info("Получение списка продуктов по id {}", id);

        var product = productInterfaceService.getProductById(id);

        if (product.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        } else {
            return ResponseEntity
                    .ok()
                    .body(product);
        }
    }

    @PutMapping
    public ResponseEntity<Void> updateProduct(@RequestBody Product product) {
        log.info("Обновление продукта {}", product);

        var isUpdate = productInterfaceService.update(product);

        if (isUpdate) {
            return ResponseEntity
                    .accepted()
                    .build();
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable long id) {
        log.info("Удаление продукта id {}", id);

        var isDeleted = productInterfaceService.delete(id);

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
