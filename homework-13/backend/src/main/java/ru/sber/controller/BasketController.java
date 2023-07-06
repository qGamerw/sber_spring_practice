package ru.sber.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.entity.Product;
import ru.sber.model.PaymentDetails;
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

    @PostMapping("/{idClient}")
    public ResponseEntity<Void> addProductInBasket(@PathVariable long idClient, @RequestBody Product product) throws URISyntaxException {
        log.info("Добавляет продукт в корзину с id {} -> {} клиенту id {}",
                product.getId(), product.getAmount(), idClient);

        var isCreated = basketInterfaceService.add(idClient, product);

        if (isCreated) {
            return ResponseEntity
                    .created(new URI("baskets/clients/" + idClient + "/" + product.getId()))
                    .build();
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @PutMapping("/{idClient}")
    public ResponseEntity<Void> updateProductInBasket(@PathVariable long idClient, @RequestBody Product product) throws URISyntaxException {
        log.info("Обновляет количество продукта в корзине с id: {} -> {}", product.getId(), 1);

        var isUpdated = basketInterfaceService.updateProduct(idClient, product);

        if (isUpdated) {
            return ResponseEntity
                    .created(new URI("baskets/clients/" + idClient + "/" + product.getId()))
                    .build();
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @DeleteMapping("/{idClient}")
    public ResponseEntity<Void> deleteProductInBasket(@PathVariable long idClient, @RequestBody Product product) {
        log.info("Удаляет продукт в корзине с id: {} у клиента {}", product.getId(), idClient);

        boolean isDelete = basketInterfaceService.deleteProduct(idClient, product);
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

    @GetMapping("/{idClient}")
    public ResponseEntity<Void> isProductInBasket(@PathVariable long idClient) {
        log.info("Проверяем есть ли продукт в корзине у клиента {}", idClient);

        boolean isDelete = basketInterfaceService.isBasket(idClient);

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
