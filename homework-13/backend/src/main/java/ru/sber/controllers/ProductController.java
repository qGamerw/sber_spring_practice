package ru.sber.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.sber.entity.Product;
import ru.sber.services.ProductServiceInterface;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**Контроллер для взаимодействия с продуктом*/
@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("products")
public class ProductController {

    private final ProductServiceInterface productServiceInterface;

    @Autowired
    public ProductController(ProductServiceInterface productServiceInterface) {
        this.productServiceInterface = productServiceInterface;
    }

    /**
     * Добавляет новый товар
     *
     * @param product Добавляемый товар
     * @return Возвращает статус добавления товара
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> addProduct(@Valid @RequestBody Product product) {
        long productId = productServiceInterface.addProduct(product);
        log.info("Добавление товара {}", product);
        return ResponseEntity.created(URI.create("products/" + productId)).build();
    }

    /**
     * Выдает все товары по фильтру
     *
     * @param name Название товара (фильтр)
     * @return Возвращает список найденных товаров
     */
    @GetMapping
    public List<Product> getProducts(@RequestParam(required = false) String name) {

        if (name == null) {
            name = "";
            log.info("Вывод всех товаров");
        } else {
            log.info("Поиск товаров по имени {}", name);
        }

        return productServiceInterface.findAllByName(name);
    }

    /**
     * Получение товара по id
     *
     * @param id Идентификатор для поиска
     * @return Возвращает найденный товар
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProducts(@PathVariable long id) {
        log.info("Получение товара по id");
        Optional<Product> product = productServiceInterface.findById(id);

        if (product.isPresent()) {
            return ResponseEntity.ok().body(product.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Обновление информации о товаре
     *
     * @param product Информация об обновленном товаре
     * @return Возвращает обновленный товар
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<?> updateProduct(@Valid @RequestBody Product product) {
        productServiceInterface.updateProduct(product);
        log.info("Обновление информации о товаре");
        return ResponseEntity.ok().body(product);
    }

    /**
     * Удаление товара по id
     *
     * @param id Идентификатор товара
     * @return Возвращает статус удаления товара
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id) {
        log.info("Удаление продукта по id");
        boolean isDeleted = productServiceInterface.deleteById(id);

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}