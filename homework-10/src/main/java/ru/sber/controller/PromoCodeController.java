package ru.sber.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.entity.PromoCode;
import ru.sber.services.PromoCodeInterfaceService;

import java.net.URI;

/**
 * Контролер для взаимодействия с промокодом
 */
@Slf4j
@RestController
@RequestMapping("promocode")
public class PromoCodeController {
    private final PromoCodeInterfaceService promoCodeInterfaceService;

    @Autowired
    public PromoCodeController(PromoCodeInterfaceService promoCodeInterfaceService) {
        this.promoCodeInterfaceService = promoCodeInterfaceService;
    }

    @PostMapping
    public ResponseEntity<?> addPromoCode(@RequestBody PromoCode promoCode) {
        log.info("Добавляет промокода {}", promoCode);

        return ResponseEntity
                .created(URI.create("promocode/" + promoCodeInterfaceService.addPromoCode(promoCode)))
                .build();
    }

    @GetMapping
    public ResponseEntity<PromoCode> getPromoCode(@RequestParam long id) {
        log.info("Получает промокода с id {}", id);

        var promoCode = promoCodeInterfaceService.getPromoCodeById(id);

        if (promoCode.isPresent()) {
            return ResponseEntity
                    .ok()
                    .body(promoCode.get());
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @PutMapping
    public ResponseEntity<?> updatePromoCode(@RequestBody PromoCode promoCode) {
        log.info("Обновление промокода {}", promoCode);

        var isUpdate = promoCodeInterfaceService.update(promoCode);

        if (isUpdate) {
            return ResponseEntity
                    .accepted()
                    .build();
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deletePromoCode(@RequestParam long id) {
        log.info("Удаляет промокода с id {}", id);

        boolean isDeleted = promoCodeInterfaceService.deletePromoCodeById(id);

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
