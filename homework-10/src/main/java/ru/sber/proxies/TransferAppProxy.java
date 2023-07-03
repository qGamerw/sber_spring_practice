package ru.sber.proxies;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sber.exception.IncorrectAmountException;
import ru.sber.exception.NotEnoughMoneyException;

import java.math.BigDecimal;

/**
 * Класс для перевода денежных средств
 */
@Slf4j
@Component
public class TransferAppProxy implements TransferInterfaceProxy {
    private final BankClientsInterfaceProxy bankClientsInterfaceProxy;

    public TransferAppProxy(BankClientsInterfaceProxy bankClientsInterfaceProxy) {
        this.bankClientsInterfaceProxy = bankClientsInterfaceProxy;
    }

    @Override
    public boolean transferToPay(BigDecimal sum, long card) {
        log.info("Номер карты {} сумма платежа {}", card, sum);

        if (sum.compareTo(BigDecimal.valueOf(0)) < 0) {
            throw new IncorrectAmountException("Некорректное значение");
        }

        if (bankClientsInterfaceProxy.isBankClient(card)
                && bankClientsInterfaceProxy.getCashByIdClient(card).compareTo(sum) > 0) {

            return true;
        } else {
            throw new NotEnoughMoneyException("Недостаточно средств для оплаты");
        }
    }
}
