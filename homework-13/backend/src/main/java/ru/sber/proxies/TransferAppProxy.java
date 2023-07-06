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
    private final BankUsersInterfaceProxy bankUsersInterfaceProxy;

    public TransferAppProxy(BankUsersInterfaceProxy bankUsersInterfaceProxy) {
        this.bankUsersInterfaceProxy = bankUsersInterfaceProxy;
    }

    @Override
    public boolean transferToPay(BigDecimal sum, long card) {
        log.info("Номер карты {} сумма платежа {}", card, sum);

        if (sum.compareTo(BigDecimal.valueOf(0)) < 0) {
            throw new IncorrectAmountException("Некорректное значение");
        }

        if (bankUsersInterfaceProxy.isBankUser(card)
                && bankUsersInterfaceProxy.getCashByIdUser(card).compareTo(sum) > 0) {

            return true;
        } else {
            throw new NotEnoughMoneyException("Недостаточно средств для оплаты");
        }
    }
}
