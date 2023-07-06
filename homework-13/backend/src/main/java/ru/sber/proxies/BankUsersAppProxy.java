package ru.sber.proxies;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sber.exception.BankUserException;
import ru.sber.model.Card;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для проверки списка клиентов банка
 */
@Slf4j
@Component
public class BankUsersAppProxy implements BankUsersInterfaceProxy {

    private final List<Card> cards = new ArrayList<>(List.of(
            new Card(1111, BigDecimal.valueOf(10000)),
            new Card(2222, BigDecimal.valueOf(2000)),
            new Card(3333, BigDecimal.valueOf(20))
    ));

    @Override
    public boolean isBankUser(long card) {
        log.info("Проверка есть ли указанная карта");
        for (Card item : cards) {
            if (card == item.getIdCard()) {
                return true;
            }
        }
        throw new BankUserException("Не является клиентом банка");
    }

    @Override
    public BigDecimal getCashByIdUser(long card) {
        for (Card item : cards) {
            if (card == item.getIdCard()) {
                return item.getAccount();
            }
        }
        throw new BankUserException("Ошибка получения средств");
    }
}
