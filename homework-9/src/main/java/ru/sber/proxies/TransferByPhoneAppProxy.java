package ru.sber.proxies;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sber.exception.IncorrectAmountException;
import ru.sber.exception.NotEnoughMoneyException;

import java.math.BigDecimal;

/**
 * Класс для перевода денежных средств по номеру телефона
 */
@Slf4j
@Component
public class TransferByPhoneAppProxy implements TransferByPhoneInterfaceProxy {
    private final BankClientsInterfaceProxy bankClientsInterfaceProxy;

    public TransferByPhoneAppProxy(BankClientsInterfaceProxy bankClientsInterfaceProxy) {
        this.bankClientsInterfaceProxy = bankClientsInterfaceProxy;
    }

    @Override
    public BigDecimal transferToPay(BigDecimal sum, long card) {
        log.info("Номер карты {} сумма платежа {}", card, sum);

        if (sum.compareTo(BigDecimal.valueOf(0)) < 0) {
            throw new IncorrectAmountException("Некорректное значение");
        }

        if (bankClientsInterfaceProxy.isBankClient(card)
                && bankClientsInterfaceProxy.getCashByIdClient(card).compareTo(sum) > 0) {

            return bankClientsInterfaceProxy.getCashByIdClient(card).subtract(sum);
        } else {
            throw new NotEnoughMoneyException("Недостаточно средств для оплаты");
        }
    }
}
