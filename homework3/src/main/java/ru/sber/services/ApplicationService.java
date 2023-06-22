package ru.sber.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.exception.BankClientException;
import ru.sber.exception.TransferByPhoneException;
import ru.sber.model.Client;
import ru.sber.proxies.BankClientsAppProxy;
import ru.sber.proxies.BankClientsInterfaceProxy;
import ru.sber.proxies.TransferByPhoneInterfaceProxy;
import ru.sber.repositories.DBTranslationHistoryRepository;

import java.math.BigDecimal;

/*
 * Класс для реализации логики провеки существования пользователя и
 * перевода средств на телефон
 */
@Service
public class ApplicationService {
    private final BankClientsInterfaceProxy bankClientsInterfaceProxy;
    private final TransferByPhoneInterfaceProxy transferByPhoneInterfaceProxy;
    private DBTranslationHistoryRepository dbTranslationHistoryRepository;

    @Autowired
    public ApplicationService(BankClientsAppProxy bankClientsAppProxy,
                              TransferByPhoneInterfaceProxy transferByPhoneInterfaceProxy) {
        this.bankClientsInterfaceProxy = bankClientsAppProxy;
        this.transferByPhoneInterfaceProxy = transferByPhoneInterfaceProxy;
    }

    @Autowired
    public void initDB(DBTranslationHistoryRepository dbTranslationHistoryRepository) {
        this.dbTranslationHistoryRepository = DBTranslationHistoryRepository.getInstance();
    }

    public void checkUser(Client client) throws BankClientException {
        if (bankClientsInterfaceProxy.getBankClient(client)) {
            System.out.println("Пользователь " + client.getName() +
                    " с номером " + client.getPhone() + " является клиентом банка.");
        } else {
            throw new BankClientException("Пользователь не является клиентом банка.");
        }
    }

    public void sendMoney(String phone, BigDecimal bigDecimal) {
        try {
            transferByPhoneInterfaceProxy.TransferByPhone(phone, bigDecimal);
            dbTranslationHistoryRepository.addTranslationHistory(phone, bigDecimal);
        } catch (TransferByPhoneException transferByPhoneException) {
            transferByPhoneException.printStackTrace();
        }
    }

    public void printHistory() {
        System.out.println("История платежей:");
        dbTranslationHistoryRepository.getTranslationHistory().forEach(System.out::println);
    }
}
