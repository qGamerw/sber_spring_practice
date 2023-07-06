package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.entity.User;
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
    private final UserInterfaceService userInterfaceService;

    public PaymentService(BasketInterfaceService basketInterfaceService,
                          TransferInterfaceProxy transferInterfaceProxy,
                          PromoCodeInterfaceService promoCodeInterfaceService,
                          UserInterfaceService userInterfaceService) {

        this.basketInterfaceService = basketInterfaceService;
        this.transferInterfaceProxy = transferInterfaceProxy;
        this.promoCodeInterfaceService = promoCodeInterfaceService;
        this.userInterfaceService = userInterfaceService;
    }

    @Transactional
    @Override
    public boolean pay(PaymentDetails paymentDetails) {
        log.info("PaymentService оплата товара в сервисе");

        isChecks(paymentDetails);

        var price = userInterfaceService.getPrice(paymentDetails.getIdUser(), paymentDetails.getIdPromoCode());
        var idCard = userInterfaceService.getIdCard(paymentDetails.getIdUser());
        var isRemove = basketInterfaceService.basketCleaning(paymentDetails.getIdUser());

        if (!isRemove) {
            throw new RemoveProductException("Ошибка удаления товара");
        }

        return transferInterfaceProxy.transferToPay(price, idCard);
    }

    private void isChecks(PaymentDetails paymentDetails) {
        log.info("PaymentService проверяем исключительные ситуации у клиента {} при оплате", paymentDetails.getIdUser());

        var isBankUser = basketInterfaceService.isBasket(paymentDetails.getIdUser());
        if (!isBankUser) {
            throw new EmptyBasketException("Корзина пустая");
        }

        var user = new User();
        user.setId(paymentDetails.getIdUser());
        var isInsufficientQuantity = basketInterfaceService.isCountProduct(user);
        if (!isInsufficientQuantity) {
            throw new InsufficientQuantityException("Недостаточно количество товара на складе");
        }

        var isPromoCode = promoCodeInterfaceService.isPromoCodeById(paymentDetails.getIdPromoCode());
        if (!isPromoCode) {
            throw new NoFoundPromoCodeException("Промокод не найден");
        }
    }
}
