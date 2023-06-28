package ru.sber.services;

import ru.sber.model.PaymentDetails;

public interface PaymentInterfaceService {
    boolean pay(PaymentDetails paymentDetails);
}
