package ru.sber.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.sber.entity.Payment;
import ru.sber.services.PaymentServiceInterface;

/**Контроллер для оплаты товара*/
@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("payment")
public class PaymentController {
    PaymentServiceInterface paymentServiceInterface;

    @Autowired
    public PaymentController(PaymentServiceInterface paymentServiceInterface) {
        this.paymentServiceInterface = paymentServiceInterface;
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> pay(@Valid @RequestBody Payment payment) {

        log.info("Оплата товара");
        boolean isPay = paymentServiceInterface.pay(payment);

        if (isPay) {
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.badRequest().body("Недостаточно средств");
        }

    }
}
