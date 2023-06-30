package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.exception.EmptyBasketException;
import ru.sber.exception.InsufficientQuantityException;
import ru.sber.exception.RemoveProductException;
import ru.sber.model.PaymentDetails;
import ru.sber.proxies.TransferByPhoneAppProxy;
import ru.sber.repository.BasketRepository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.List;

/**
 * Класс для оплаты продуктов в корзине
 */
@Slf4j
@Service
public class PaymentService implements PaymentInterfaceService {
    private final BasketRepository basketRepository;
    private final TransferByPhoneAppProxy transferByPhoneAppProxy;

    public PaymentService(BasketRepository basketRepository,
                          TransferByPhoneAppProxy transferByPhoneAppProxy) {
        this.basketRepository = basketRepository;
        this.transferByPhoneAppProxy = transferByPhoneAppProxy;
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

        return transferByPhoneAppProxy.transferToPay(price, idCard);
    }
}
