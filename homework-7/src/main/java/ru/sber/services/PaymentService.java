package ru.sber.services;

import org.springframework.stereotype.Service;
import ru.sber.exception.EmptyBasketException;
import ru.sber.exception.NotEnoughMoneyException;
import ru.sber.model.PaymentDetails;
import ru.sber.repository.ClientRepository;

import java.math.BigDecimal;

/**
 * Класс для оплаты продуктов в корзине
 */
@Service
public class PaymentService implements PaymentInterfaceService {
    private final ClientRepository clientRepository;

    public PaymentService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public boolean pay(PaymentDetails paymentDetails) {
        var clientOptional = clientRepository.getClientById(paymentDetails.getIdClient());
        if (clientOptional.isPresent()
                && !clientOptional.get().getBasket().getProductList().isEmpty()) {
            var client = clientOptional.get();
            var allprice = client.getBasket()
                    .getProductList()
                    .stream()
                    .map(product -> product.getPrice().multiply(BigDecimal.valueOf(product.getAmount())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            if (paymentDetails.getSum() != null && paymentDetails.getSum().compareTo(allprice) > 0) {
                return true;
            } else {
                throw new NotEnoughMoneyException("Недостаточно средств для оплаты");
            }
        }
        throw new EmptyBasketException("Пустая корзина");
    }
}
