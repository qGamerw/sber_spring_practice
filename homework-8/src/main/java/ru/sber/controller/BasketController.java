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

    @PostMapping("/add/{idClient}/{idProduct}/{count}")
    public ResponseEntity<?> addProduct(@PathVariable long idClient, @PathVariable long idProduct, @PathVariable int count) {
        log.info("Добавление продукта в корзину с id: {} -> {} клиенту {}", idProduct, count, idClient);
        boolean isAdd = basketRepository.add(idClient, idProduct, count);
        if (isAdd) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{idClient}")
    public ResponseEntity<?> updateProduct(@PathVariable long idClient, @RequestBody Product product) {
        log.info("Изменение количества продукта в корзине с id: {} -> {}", product.getId(), 1);
        boolean isUpdate = basketRepository.update(idClient, product.getId(), product.getPrice().intValue());
        if (isUpdate) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/delete/{idClient}/{idProduct}")
    public ResponseEntity<?> deleteProduct(@PathVariable long idClient, @PathVariable long idProduct) {
        log.info("Удаление продукта в корзине с id: {} у клиента {}", idProduct, idClient);
        boolean isDelete = basketRepository.delete(idClient, idProduct);
        if (isDelete) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
