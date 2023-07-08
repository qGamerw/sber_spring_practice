package ru.sber.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.sber.entities.Cart;
import ru.sber.entities.Product;
import ru.sber.services.CartService;

import java.net.URI;
import java.util.List;


/**
 * Класс для взаимодействия с корзиной пользователя
 */
@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("cart")
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/{idClient}/product/{idProduct}")
    public ResponseEntity<Cart> addProductToCart(@PathVariable long idClient,
                                                 @PathVariable Long idProduct, @RequestBody Product product) {
        log.info("Добавляет в корзину товар с id {} количеством {}", idProduct, product.getAmount());

        boolean recordInserted = cartService.addToCart(idClient, idProduct, product.getAmount());

        if (recordInserted) {
            return ResponseEntity.created(URI.create("cart/" + idClient + "/product/" + idProduct)).build();

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping("/{idCart}/product/{idProduct}")
    public ResponseEntity<Cart> updateProductAmountInCart(@PathVariable long idCart,
                                                          @PathVariable long idProduct, @RequestBody Product product) {
        log.info("Меняет имя, количество и цену у продукта с id {} в корзине {}", idProduct, idCart);

        boolean isUpdated = cartService.updateProductAmount(idCart, idProduct, product.getAmount());

        if (isUpdated) {
            return ResponseEntity
                    .ok()
                    .build();

        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{idCart}")
    public ResponseEntity<List<Product>> getListProducts(@PathVariable long idCart) {
        log.info("Получает продукты в корзине с id {}", idCart);

        List<Product> productsInCart = cartService.getListOfProductsInCart(idCart);
        return ResponseEntity
                .ok()
                .body(productsInCart);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @DeleteMapping("/{idCart}/product/{idProduct}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long idCart, @PathVariable long idProduct) {
        log.info("Удаляет товар из корзины с id: {}", idProduct);

        boolean isDeleted = cartService.deleteProduct(idCart, idProduct);

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
