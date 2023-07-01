package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.exception.EmptyBasketException;
import ru.sber.exception.InsufficientQuantityException;
import ru.sber.exception.RemoveProductException;
import ru.sber.model.PaymentDetails;
import ru.sber.proxies.TransferAppProxy;
import ru.sber.repository.BasketRepository;

import java.math.BigDecimal;

/**
 * Класс для оплаты продуктов в корзине
 */
@Slf4j
@Service
public class PaymentService implements PaymentInterfaceService {
    private final BasketRepository basketRepository;
    private final TransferAppProxy transferAppProxy;

    public PaymentService(BasketRepository basketRepository,
                          TransferAppProxy transferAppProxy) {
        this.basketRepository = basketRepository;
        this.transferAppProxy = transferAppProxy;
    }

    @Transactional
    @Override
    public BigDecimal pay(PaymentDetails paymentDetails) {
        log.info("Оплата товара в сервисе");

        var isBankClient = basketRepository.isBasket(paymentDetails.getIdClient());
        if (!isBankClient) {
            throw new EmptyBasketException("Корзина пустая");
        }

        var isInsufficientQuantity = basketRepository.isCountProduct(paymentDetails.getIdClient());

        if (isInsufficientQuantity) {
            throw new InsufficientQuantityException("Недостаточно количество товара на складе");
        }

        var price = basketRepository.getPrice(paymentDetails.getIdClient());
        var idCard = basketRepository.getIdCard(paymentDetails.getIdClient()).get();
        var isRemove = basketRepository.removeProductBasket(paymentDetails.getIdClient());

        if (!isRemove) {
            throw new RemoveProductException("Ошибка удаления товара");
        }

        return transferAppProxy.transferToPay(price, idCard);
    }
}
