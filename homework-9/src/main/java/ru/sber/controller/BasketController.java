package ru.sber.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.model.Product;
import ru.sber.services.BasketInterfaceService;

import java.net.URI;

/**
 * Получает запросы для взаимодействия с корзиной
 */
@Slf4j
@RestController
@RequestMapping("baskets")
public class BasketController {
    private final BasketInterfaceService basketInterfaceService;

    public BasketController(BasketInterfaceService basketInterfaceService) {
        this.basketInterfaceService = basketInterfaceService;
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestParam long idClient, @RequestBody Product product) {
        log.info("Добавляет продукт в корзину с id {} -> {} клиенту id {}", product.getIdProduct(), product.getCount(), idClient);

        var isCreated = basketInterfaceService.addProduct(idClient, product);
        if (isCreated) {
            return ResponseEntity.created(URI.create("basket/" + product.getIdProduct())).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestParam long idClient, @RequestBody Product product) {
        log.info("Обновляет количество продукта в корзине с id: {} -> {}", product.getIdProduct(), 1);

        var isUpdated = basketInterfaceService.updateProduct(idClient, product);
        if (isUpdated) {
            return ResponseEntity.created(URI.create("basket/" + product.getIdProduct())).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProduct(@RequestParam long idClient, @RequestBody Product product) {
        log.info("Удаляет продукт в корзине с id: {} у клиента {}", product.getIdProduct(), idClient);

        boolean isDelete = basketInterfaceService.deleteProduct(idClient, product);
        log.info(String.valueOf(isDelete));
        if (isDelete) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
