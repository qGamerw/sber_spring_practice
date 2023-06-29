package ru.sber.proxies;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sber.exception.BankClientException;
import ru.sber.model.Card;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для проверки списка клиентов банка
 */
@Slf4j
@Component
public class BankClientsAppProxy implements BankClientsInterfaceProxy {

    private List<Card> cards = new ArrayList<>(List.of(
            new Card(8, BigDecimal.valueOf(1000)),
            new Card(2, BigDecimal.valueOf(2000)),
            new Card(3, BigDecimal.valueOf(20))
    ));

    @Override
    public boolean isBankClient(long card) {
        log.info("Проверка есть ли указанная карта");
        for (Card item : cards) {
            if (card == item.getIdCard()) {
                return true;
            }
        }
        throw new BankClientException("Не является клиентом банка");
    }

    public BigDecimal getCashByIdClient(long card) {
        for (Card item : cards) {
            if (card == item.getIdCard()) {
                return item.getAccount();
            }
        }
        return BigDecimal.valueOf(0);
    }

    @Override
    public List<Card> getCards() {
        return cards;
    }
}
