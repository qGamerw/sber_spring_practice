package ru.sber.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sber.entity.Client;
import ru.sber.services.PaymentInterfaceService;

import java.net.URI;


/**
 * Получает запросы для платы товаров
 */
@Slf4j
@RestController
@RequestMapping("payment")
public class PaymentController {

    private final PaymentInterfaceService paymentInterfaceService;

    public PaymentController(PaymentInterfaceService paymentInterfaceService) {
        this.paymentInterfaceService = paymentInterfaceService;
    }

    @PostMapping
    public ResponseEntity<?> payProduct(@RequestBody Client client) {
        log.info("Оплата товара");

        return ResponseEntity.accepted().location(
                URI.create("payment/" + paymentInterfaceService.pay(client))).build();
    }
}
