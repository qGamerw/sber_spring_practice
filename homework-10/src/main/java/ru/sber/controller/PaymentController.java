package ru.sber.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sber.model.PaymentDetails;
import ru.sber.services.PaymentInterfaceService;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Получает запросы для оплаты товаров
 */
@Slf4j
@RestController
@RequestMapping("payments")
public class PaymentController {
    private final PaymentInterfaceService paymentInterfaceService;

    public PaymentController(PaymentInterfaceService paymentInterfaceService) {
        this.paymentInterfaceService = paymentInterfaceService;
    }

    @PostMapping
    public ResponseEntity<Void> payProductsInBasket(@RequestBody PaymentDetails paymentDetails) throws URISyntaxException {
        log.info("Оплата товара клиента id {} с промокодом {}",
                paymentDetails.getIdClient(), paymentDetails.getIdPromoCode());

        var isPay = paymentInterfaceService.pay(paymentDetails);
        log.info("Оплата клиента с id {} {}", paymentDetails.getIdClient(), isPay ? "удалились" : "не удалились");

        if (isPay) {
            return ResponseEntity
                    .ok()
                    .location(new URI("payments/clients/" + paymentDetails.getIdClient()))
                    .build();
        } else {
            return ResponseEntity
                    .badRequest()
                    .build();
        }
    }
}
