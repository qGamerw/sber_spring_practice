package ru.sber.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sber.services.PaymentInterfaceService;

@Slf4j
@RestController
@RequestMapping("payment")
public class PaymentController {

    private PaymentInterfaceService paymentInterfaceService;
    public PaymentController(PaymentInterfaceService paymentInterfaceService) {
        this.paymentInterfaceService = paymentInterfaceService;
    }


}
