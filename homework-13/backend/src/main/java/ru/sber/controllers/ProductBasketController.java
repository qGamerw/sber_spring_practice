package ru.sber.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.sber.entity.Basket;
import ru.sber.entity.Product;
import ru.sber.services.BasketServiceInterface;

import java.net.URI;
import java.util.List;

/**Корзина клиента*/
@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("basket")
public class ProductBasketController {

    private final BasketServiceInterface basketServiceInterface;

    @Autowired
    public ProductBasketController(BasketServiceInterface basketServiceInterface) {
        this.basketServiceInterface = basketServiceInterface;
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/{idClient}/product/{idProduct}")
    public ResponseEntity<Basket> addProductToCart(@PathVariable long idClient, @PathVariable Long idProduct, @RequestBody Product product) {

        log.info("Добавление в корзину товара с id: {} количеством {}", idProduct, product.getAmount());

        boolean recordInserted = basketServiceInterface.addToCart(idClient, idProduct, product.getAmount());

        if (recordInserted) {
            return ResponseEntity.created(URI.create("cart/" + idClient + "/product/" + idProduct)).build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping("/{idCart}/product/{idProduct}")
    public ResponseEntity<Basket> updateProductAmountInCart(@PathVariable long idCart, @PathVariable long idProduct, @RequestBody Product product) {

        log.info("Изменение количества товара в корзине");

        boolean recordUpdated = basketServiceInterface.updateProductAmount(idCart, idProduct, product.getAmount());

        if (recordUpdated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{idCart}")
    public ResponseEntity<?> getProducts(@PathVariable long idCart) {

        log.info("Получение корзины пользователя с id {}", idCart);

        List<Product> productsInCart = basketServiceInterface.getListOfProductsInCart(idCart);

        return ResponseEntity.ok().body(productsInCart);


    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @DeleteMapping("/{idCart}/product/{idProduct}")
    public ResponseEntity<?> deleteProduct(@PathVariable long idCart, @PathVariable long idProduct) {

        log.info("Удаление из корзины товара с id {}", idProduct);

        boolean isDeleted = basketServiceInterface.deleteProduct(idCart, idProduct);

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

}
