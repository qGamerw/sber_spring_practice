package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.entity.Client;
import ru.sber.exception.EmptyBasketException;
import ru.sber.exception.InsufficientQuantityException;
import ru.sber.exception.NoFoundPromoCodeException;
import ru.sber.exception.RemoveProductException;
import ru.sber.model.PaymentDetails;
import ru.sber.proxies.TransferInterfaceProxy;

/**
 * Класс для оплаты продуктов в корзине
 */
@Slf4j
@Service
public class PaymentService implements PaymentInterfaceService {
    private final BasketInterfaceService basketInterfaceService;
    private final TransferInterfaceProxy transferInterfaceProxy;
    private final PromoCodeInterfaceService promoCodeInterfaceService;
    private final ClientInterfaceService clientInterfaceService;

    @Autowired
    public PaymentService(BasketInterfaceService basketInterfaceService,
                          TransferInterfaceProxy transferInterfaceProxy,
                          ClientInterfaceService clientInterfaceService,
                          PromoCodeInterfaceService promoCodeInterfaceService) {

        this.basketInterfaceService = basketInterfaceService;
        this.transferInterfaceProxy = transferInterfaceProxy;
        this.promoCodeInterfaceService = promoCodeInterfaceService;
        this.clientInterfaceService = clientInterfaceService;
    }

    @Transactional
    @Override
    public boolean pay(PaymentDetails paymentDetails) {
        log.info("PaymentService оплата товара в сервисе");

        isChecks(paymentDetails);

        var price = clientInterfaceService.getPrice(paymentDetails.getIdClient(), paymentDetails.getIdPromoCode());
        var idCard = clientInterfaceService.getIdCard(paymentDetails.getIdClient());
        var isRemove = basketInterfaceService.basketCleaning(paymentDetails.getIdClient());

        if (!isRemove) {
            throw new RemoveProductException("Ошибка удаления товара");
        }

        return transferInterfaceProxy.transferToPay(price, idCard);
    }

    private void isChecks(PaymentDetails paymentDetails) {
        log.info("PaymentService проверяем исключительные ситуации у клиента {} при оплате", paymentDetails.getIdClient());

        var isBankClient = basketInterfaceService.isBasket(paymentDetails.getIdClient());
        if (!isBankClient) {
            throw new EmptyBasketException("Корзина пустая");
        }

        var client = new Client();
        client.setId(paymentDetails.getIdClient());
        var isInsufficientQuantity = basketInterfaceService.isCountProduct(client);
        if (!isInsufficientQuantity) {
            throw new InsufficientQuantityException("Недостаточно количество товара на складе");
        }

//        var isPromoCode = promoCodeInterfaceService.isPromoCodeById(paymentDetails.getIdPromoCode());
//        if (!isPromoCode) {
//            throw new NoFoundPromoCodeException("Промокод не найден");
//        }
    }
}
