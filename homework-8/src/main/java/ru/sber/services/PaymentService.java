package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sber.exception.EmptyBasketException;
import ru.sber.model.PaymentDetails;
import ru.sber.proxies.TransferByPhoneAppProxy;
import ru.sber.repository.BasketRepository;
import ru.sber.repository.ClientRepository;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Класс для оплаты продуктов в корзине
 */
@Slf4j
@Service
public class PaymentService implements PaymentInterfaceService {
    private final BasketRepository basketRepository;
    private final TransferByPhoneAppProxy transferByPhoneAppProxy;

    public PaymentService(BasketRepository basketRepository, TransferByPhoneAppProxy transferByPhoneAppProxy) {
        this.basketRepository = basketRepository;
        this.transferByPhoneAppProxy = transferByPhoneAppProxy;
    }

    @Override
    public BigDecimal pay(PaymentDetails paymentDetails) {
        log.info("Оплата товара");
        return transferByPhoneAppProxy.transferToPay(basketRepository.getPrice(paymentDetails.getIdClient()), paymentDetails.getIdClient());

    }

}
