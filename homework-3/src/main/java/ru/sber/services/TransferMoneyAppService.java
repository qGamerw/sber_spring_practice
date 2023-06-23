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

/**
 * Интерфейс для входа в приложение, перевода денежных средств и
 * вывода истории перевода средств
 */
@Service
public class TransferMoneyAppService implements TransferMoneyInterfaceService {
    private final BankClientsInterfaceProxy bankClientsInterfaceProxy;
    private final TransferByPhoneInterfaceProxy transferByPhoneInterfaceProxy;
    private DBTranslationHistoryRepository dbTranslationHistoryRepository;

    @Autowired
    public TransferMoneyAppService(BankClientsAppProxy bankClientsAppProxy,
                                   TransferByPhoneInterfaceProxy transferByPhoneInterfaceProxy) {
        this.bankClientsInterfaceProxy = bankClientsAppProxy;
        this.transferByPhoneInterfaceProxy = transferByPhoneInterfaceProxy;
    }

    @Autowired
    @Override
    public void initDataBase(DBTranslationHistoryRepository dbTranslationHistoryRepository) {
        this.dbTranslationHistoryRepository = DBTranslationHistoryRepository.getInstance();
    }

    @Override
    public void signInBankApp(Client client) throws BankClientException {
        if (bankClientsInterfaceProxy.isBankClient(client)) {
            System.out.println("Пользователь " + client.phone() +
                    " с номером " + client.phone() + " является клиентом банка.");
        } else {
            throw new BankClientException("Пользователь не является клиентом банка.");
        }
    }

    @Override
    public void sendMoneyToPhone(String phone, BigDecimal bigDecimal) {
        try {
            transferByPhoneInterfaceProxy.transferByPhone(phone, bigDecimal);
            dbTranslationHistoryRepository.addTranslationHistory(phone, bigDecimal);
        } catch (TransferByPhoneException transferByPhoneException) {
            transferByPhoneException.printStackTrace();
        }
    }

    @Override
    public void printTranslationHistory() {
        System.out.println("История платежей:");
        dbTranslationHistoryRepository.getTranslationHistory().
                forEach(System.out::println);
    }
}
