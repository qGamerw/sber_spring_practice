package ru.sber.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.model.PaymentDetails;
import ru.sber.services.PaymentInterfaceService;

@Slf4j
@RestController
@RequestMapping("payment")
public class PaymentController {

    private final PaymentInterfaceService paymentInterfaceService;
    public PaymentController(PaymentInterfaceService paymentInterfaceService) {
        this.paymentInterfaceService = paymentInterfaceService;
    }

    @PostMapping("/pay/{sum}")
    public ResponseEntity<?> payProduct(@RequestBody PaymentDetails paymentDetails){
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(paymentInterfaceService.pay(paymentDetails));
    }
}
