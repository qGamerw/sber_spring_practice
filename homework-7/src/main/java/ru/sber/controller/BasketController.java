package ru.sber.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.model.Product;
import ru.sber.repository.BasketRepository;

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

    @PostMapping("/add/{id}/{count}")
    public ResponseEntity<?> addProduct(@PathVariable long id, @PathVariable int count) {
        log.info("Добавление продукта в корзину с id: {} -> {}", id, count);
        boolean isAdd = basketRepository.add(id, count);
        if (isAdd) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody Product product) {
        log.info("Изменение количества продукта в корзине с id: {} -> {}", product.getId(), product.getAmount());
        boolean isUpdate = basketRepository.update(product.getId(), product.getAmount());
        if (isUpdate) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id) {
        log.info("Удаление продукта в корзине с id: {}", id);
        boolean isDelete = basketRepository.delete(id);
        if (isDelete) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
