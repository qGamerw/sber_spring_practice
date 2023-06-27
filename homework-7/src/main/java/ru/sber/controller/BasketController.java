package ru.sber.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.sber.model.Basket;
import ru.sber.model.Product;
import ru.sber.repository.BasketRepository;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("baskets")
public class BasketController {
    private final BasketRepository basketRepository;

    public BasketController(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }


    @PostMapping("/add/{id}/{count}")
    public List<Product> addProduct(@PathVariable long id, @PathVariable int count){
        log.info("Добавление продукта в корзину с id: {} -> {}", id, count);
        return basketRepository.add(id, count);
    }

    @PutMapping
    public List<Product> updateProduct(@RequestBody Product product){
        log.info("Изменение количества продукта в корзине с id: {} -> {}", product.getId(), product.getAmount());
        return basketRepository.update(product.getId(), product.getAmount());
    }

    @PostMapping("/delete/{id}")
    public List<Product> deleteProduct(@PathVariable long id){
        log.info("Удаление продукта в корзине с id: {}", id);
        return basketRepository.delete(id);
    }
}
