package ru.sber.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.model.PaymentDetails;
import ru.sber.services.PaymentInterfaceService;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Получает запросы для оплаты товаров
 */
@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("payments")
public class PaymentController {
    private final PaymentInterfaceService paymentInterfaceService;

    public PaymentController(PaymentInterfaceService paymentInterfaceService) {
        this.paymentInterfaceService = paymentInterfaceService;
    }

    @PostMapping
    public ResponseEntity<Void> payProductsInBasket(@RequestBody PaymentDetails paymentDetails) throws URISyntaxException {
        log.info("Оплата товара клиента id {} с промокодом {}",
                paymentDetails.getIdUser(), paymentDetails.getIdPromoCode());

        var isPay = paymentInterfaceService.pay(paymentDetails);
        log.info("Оплата клиента с id {} {}", paymentDetails.getIdUser(), isPay ? "удалились" : "не удалились");

        if (isPay) {
            return ResponseEntity
                    .ok()
                    .location(new URI("payments/users/" + paymentDetails.getIdUser()))
                    .build();
        } else {
            return ResponseEntity
                    .badRequest()
                    .build();
        }
    }
}
