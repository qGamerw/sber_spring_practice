package ru.sber.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.entity.Product;
import ru.sber.services.BasketInterfaceService;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Получает запросы для взаимодействия с корзиной
 */
@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("baskets")
public class BasketController {
    private final BasketInterfaceService basketInterfaceService;

    public BasketController(BasketInterfaceService basketInterfaceService) {
        this.basketInterfaceService = basketInterfaceService;
    }

    @PostMapping("/{idUser}")
    public ResponseEntity<Void> addProductInBasket(@PathVariable long idUser, @RequestBody Product product) throws URISyntaxException {
        log.info("Добавляет продукт в корзину с id {} -> {} клиенту id {}",
                product.getId(), product.getAmount(), idUser);

        var isCreated = basketInterfaceService.add(idUser, product);

        if (isCreated) {
            return ResponseEntity
                    .created(new URI("baskets/users/" + idUser + "/" + product.getId()))
                    .build();
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @PutMapping("/{idUser}")
    public ResponseEntity<Void> updateProductInBasket(@PathVariable long idUser, @RequestBody Product product) throws URISyntaxException {
        log.info("Обновляет количество продукта в корзине с id: {} -> {}", product.getId(), 1);

        var isUpdated = basketInterfaceService.updateProduct(idUser, product);

        if (isUpdated) {
            return ResponseEntity
                    .created(new URI("baskets/users/" + idUser + "/" + product.getId()))
                    .build();
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @DeleteMapping("/{idUser}")
    public ResponseEntity<Void> deleteProductInBasket(@PathVariable long idUser, @RequestBody Product product) {
        log.info("Удаляет продукт в корзине с id: {} у клиента {}", product.getId(), idUser);

        boolean isDelete = basketInterfaceService.deleteProduct(idUser, product);
        log.info("Продукты {}", isDelete ? "удалились" : "не удалились");

        if (isDelete) {
            return ResponseEntity
                    .noContent()
                    .build();
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @GetMapping("/{idUser}")
    public ResponseEntity<Void> isProductInBasket(@PathVariable long idUser) {
        log.info("Проверяем есть ли продукт в корзине у клиента {}", idUser);

        boolean isDelete = basketInterfaceService.isBasket(idUser);

        if (isDelete) {
            return ResponseEntity
                    .ok()
                    .build();
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }
}
