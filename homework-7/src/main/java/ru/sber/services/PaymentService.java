package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sber.exception.EmptyBasketException;
import ru.sber.model.PaymentDetails;
import ru.sber.proxies.TransferByPhoneAppProxy;
import ru.sber.repository.ClientRepository;

import java.math.BigDecimal;

/**
 * Класс для оплаты продуктов в корзине
 */
@Slf4j
@Service
public class PaymentService implements PaymentInterfaceService {
    private final ClientRepository clientRepository;
    private final TransferByPhoneAppProxy transferByPhoneAppProxy;

    public PaymentService(ClientRepository clientRepository, TransferByPhoneAppProxy transferByPhoneAppProxy) {
        this.clientRepository = clientRepository;
        this.transferByPhoneAppProxy = transferByPhoneAppProxy;
    }

    @Override
    public boolean pay(PaymentDetails paymentDetails) {
        log.info("Оплата товара");
        var clientOptional = clientRepository.getClientById(paymentDetails.getIdClient());
        if (clientOptional.isPresent()
                && !clientOptional.get().getBasket().getProductList().isEmpty()) {
            var allPrice = clientOptional.get().getBasket()
                    .getProductList()
                    .stream()
                    .map(product -> product.getPrice().multiply(BigDecimal.valueOf(product.getAmount())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            return transferByPhoneAppProxy.transferToPay(clientOptional.get().getCard(), allPrice);
        }
        throw new EmptyBasketException("Пустая корзина");
    }
}
