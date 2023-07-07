package ru.sber.proxies;

import org.springframework.stereotype.Component;
import ru.sber.exception.CardIsNotExistException;
import ru.sber.entity.BankCard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Заглушка для взаимодействия с банком
 */
@Component
public class BankAppProxy implements BankAppProxyInterface {

    List<BankCard> cards = new ArrayList<>(List.of(
            new BankCard(1111, BigDecimal.valueOf(10000)),
            new BankCard(2222, BigDecimal.valueOf(2000)),
            new BankCard(3333, BigDecimal.valueOf(1000))
    ));

    @Override
    public BigDecimal getAmountOfMoneyInTheAccount(long numberOfCard) {
        for (var card : cards) {
            if (card.getNumberOfCard() == numberOfCard) {
                return card.getMoney();
            }
        }
        throw new CardIsNotExistException();
    }
}
