package ru.sber.services;

import ru.sber.exception.BankClientException;
import ru.sber.model.Client;
import ru.sber.repositories.DBTranslationHistoryRepository;

import java.math.BigDecimal;

/**
 * Интерфейс для входа в приложение, перевода денежных средств и
 * вывода истории перевода средств
 */
public interface TransferMoneyInterfaceService {
    /**
     * Инициализирует базу данных
     *
     * @param dbTranslationHistoryRepository объект базы данных
     */
    void initDataBase(DBTranslationHistoryRepository dbTranslationHistoryRepository);

    /**
     * Входит в приложение
     *
     * @param client клиент для проверки
     */
    void signInBankApp(Client client) throws BankClientException;

    /**
     * Отправляет деньги на телефон
     *
     * @param phone      номер телефона
     * @param bigDecimal сумма перевода
     */
    void sendMoneyToPhone(String phone, BigDecimal bigDecimal);

    /**
     * Печатает историю переводов
     */
    void printTranslationHistory();
}
