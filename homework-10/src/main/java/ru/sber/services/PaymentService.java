package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.entity.Client;
import ru.sber.exception.EmptyBasketException;
import ru.sber.exception.InsufficientQuantityException;
import ru.sber.exception.RemoveProductException;
import ru.sber.proxies.TransferInterfaceProxy;

import java.math.BigDecimal;

/**
 * Класс для оплаты продуктов в корзине
 */
@Slf4j
@Service
public class PaymentService implements PaymentInterfaceService {
//    private final BasketRepository basketRepository;
//    private final TransferByPhoneAppProxy transferByPhoneAppProxy;
//
//    public PaymentService(BasketRepository basketRepository,
//                          TransferByPhoneAppProxy transferByPhoneAppProxy) {
//        this.basketRepository = basketRepository;
//        this.transferByPhoneAppProxy = transferByPhoneAppProxy;

    private final BasketInterfaceService basketInterfaceService;
    private final TransferInterfaceProxy transferInterfaceProxy;

    public PaymentService(BasketInterfaceService basketInterfaceService, TransferInterfaceProxy transferInterfaceProxy) {
        this.basketInterfaceService = basketInterfaceService;
        this.transferInterfaceProxy = transferInterfaceProxy;
    }

    @Transactional
    @Override
    public BigDecimal pay(Client client) {
        log.info("Оплата товара в сервисе");

        var isBankClient = basketInterfaceService.isBasket(client.getId());

        if (!isBankClient) {
            throw new EmptyBasketException("Корзина пустая");
        }

        var isInsufficientQuantity = basketInterfaceService.isCountProduct(client);

        log.info("1 {}", isInsufficientQuantity);
        if (isInsufficientQuantity) {
            throw new InsufficientQuantityException("Недостаточно количество товара на складе");
        }

        var price = basketInterfaceService.getPrice(client.getId());
        var idCard = basketInterfaceService.getIdCard(client.getId());
        var isRemove = basketInterfaceService.removeProductBasket(client.getId());

        if (!isRemove) {
            throw new RemoveProductException("Ошибка удаления товара");
        }

        return transferInterfaceProxy.transferToPay(price, idCard);
    }
}
