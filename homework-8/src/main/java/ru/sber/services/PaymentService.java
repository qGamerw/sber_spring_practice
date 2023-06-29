package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sber.exception.EmptyBasketException;
import ru.sber.model.PaymentDetails;
import ru.sber.proxies.TransferByPhoneAppProxy;
import ru.sber.repository.BasketRepository;
import ru.sber.repository.ClientRepository;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Класс для оплаты продуктов в корзине
 */
@Slf4j
@Service
public class PaymentService implements PaymentInterfaceService {
    private final BasketRepository basketRepository;
    private final TransferByPhoneAppProxy transferByPhoneAppProxy;

    public PaymentService(BasketRepository basketRepository, TransferByPhoneAppProxy transferByPhoneAppProxy) {
        this.basketRepository = basketRepository;
        this.transferByPhoneAppProxy = transferByPhoneAppProxy;
    }

    @Override
    public BigDecimal pay(PaymentDetails paymentDetails) {
        log.info("Оплата товара");

        var isBankClient = basketRepository.isBasket(paymentDetails.getIdClient());

        if (isBankClient) {
            return transferByPhoneAppProxy.transferToPay(
                    basketRepository.getPrice(paymentDetails.getIdClient()),
                    getIdCard(paymentDetails.getIdClient()).get());
        } else {
            throw new EmptyBasketException("Корзина пустая");
        }
    }

    private Optional<Long> getIdCard(long idClient) {
        log.info("Получение id карты клиента {}", idClient);

        String JDBC = "jdbc:postgresql://localhost:5432/postgres?schema=<ukhinms>&user=postgres&password=postgre";

        var selectSql = """
                select id_card from ukhinms.clients where id = ?;
                """;

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql);) {

            prepareStatement.setLong(1, idClient);

            var resultSet = prepareStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

}
