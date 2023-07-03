package ru.sber.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.entity.PromoCode;
import ru.sber.model.RangePromoCode;
import ru.sber.services.PromoCodeInterfaceService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Контролер для взаимодействия с промокодом
 */
@Slf4j
@RestController
@RequestMapping("promo-code")
public class PromoCodeController {
    private final PromoCodeInterfaceService promoCodeInterfaceService;

    @Autowired
    public PromoCodeController(PromoCodeInterfaceService promoCodeInterfaceService) {
        this.promoCodeInterfaceService = promoCodeInterfaceService;
    }

    @PostMapping
    public ResponseEntity<Void> addPromoCode(@RequestBody PromoCode promoCode) throws URISyntaxException {
        log.info("Добавляет промокода {}", promoCode);

        return ResponseEntity
                .created(new URI("promo-code/" + promoCodeInterfaceService.addPromoCode(promoCode)))
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromoCode> getPromoCodeById(@PathVariable long id) {
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

    @GetMapping
    public ResponseEntity<List<PromoCode>> getPromoCodeListRange(@RequestBody RangePromoCode rangePromoCode) {
        log.info("Выводит скидку в диапазоне от {} до {}",
                rangePromoCode.getMinDiscount(), rangePromoCode.getMaxDiscount());

        var promoCodeList = promoCodeInterfaceService.getPromoCodeByDiscountRange(
                rangePromoCode.getMinDiscount(), rangePromoCode.getMaxDiscount());

        return ResponseEntity
                .ok()
                .body(promoCodeList);
    }

    @PutMapping
    public ResponseEntity<Void> updatePromoCode(@RequestBody PromoCode promoCode) {
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePromoCodeById(@PathVariable long id) {
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
