package ru.sber.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.sber.entities.Payment;
import ru.sber.services.PaymentService;

/**
 * Класс для взаимодействия с оплатой товаров в корзине
 */
@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("payment")
public class PaymentController {
    PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<String> pay(@Valid @RequestBody Payment payment) {
        log.info("Оплачивает товары в корзине");

        boolean isPay = paymentService.pay(payment);

        if (isPay) {
            return ResponseEntity
                    .accepted()
                    .build();

        } else {
            return ResponseEntity
                    .badRequest()
                    .body("Недостаточно средств на карте");
        }

    }
}
