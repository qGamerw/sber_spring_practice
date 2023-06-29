package ru.sber.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.model.Product;
import ru.sber.repository.BasketRepository;

import java.net.URI;

/**
 * Получает запросы для взаимодействия с корзиной
 */
@Slf4j
@RestController
@RequestMapping("baskets")
public class BasketController {
    private final BasketRepository basketRepository;

    public BasketController(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestParam long idClient, @RequestBody Product product) {
        log.info("Добавление продукта в корзину с id: {} -> {} клиенту {}", product.getId(), product.getCount(), idClient);

        var isCreated = basketRepository.add(idClient, product.getId(), product.getCount());
        if (isCreated) {
            return ResponseEntity.created(URI.create("basket/" + product.getId())).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestParam long idClient, @RequestBody Product product) {
        log.info("Изменение количества продукта в корзине с id: {} -> {}", product.getId(), 1);

        var isUpdated = basketRepository.update(idClient, product.getId(), product.getCount());
        if (isUpdated) {
            return ResponseEntity.created(URI.create("basket/" + product.getId())).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProduct(@RequestParam long idClient, @RequestParam long idProduct) {
        log.info("Удаление продукта в корзине с id: {} у клиента {}", idProduct, idClient);
        boolean isDelete = basketRepository.delete(idClient, idProduct);
        if (isDelete) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
