package ru.sber.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.sber.exception.IncorrectAmountException;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
@Repository
public class DBBasketRepository implements BasketRepository {

    public static final String JDBC = "jdbc:postgresql://localhost:5432/postgres?schema=<ukhinms>&user=postgres&password=postgre";

    @Override
    public boolean add(long idClient, long idProduct, int count) {
        log.info("Добавляет продукт в корзину клиента с id {} продукт id {} в количестве {}", idClient, idProduct, count);

        if (count < 1) {
            throw new IncorrectAmountException("Некорректное значение количества");
        }
        var insertSql = """
                INSERT INTO ukhinms.products_baskets (id_product, id_basket, count)
                VALUES (?, (select id from ukhinms.baskets where id_client = ? LIMIT 1), ?);
                """;
        var selectSql = """
                select exists(SELECT * FROM ukhinms.products where id = ?)
                """;

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatementProdCart = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
             var prepareStatement = connection.prepareStatement(selectSql);) {

            prepareStatement.setLong(1, idProduct);

            var resultSet = prepareStatement.executeQuery();

            if (resultSet.next() && resultSet.getBoolean(1)) {
                prepareStatementProdCart.setLong(1, idProduct);
                prepareStatementProdCart.setLong(2, idClient);
                prepareStatementProdCart.setInt(3, count);

                prepareStatementProdCart.executeUpdate();

                return prepareStatementProdCart.getGeneratedKeys().next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean update(long idClient, long idProduct, int count) {
        log.info("Меняет количество продуктов в корзине у клиента id {} продукта id {} на {}", idClient, idProduct, count);

        var selectSql = """
                update ukhinms.products_baskets
                set count = ?
                where id_product = ?
                  and id_basket = (select id from ukhinms.baskets where id_client = ? LIMIT 1)
                """;

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setDouble(1, count);
            prepareStatement.setLong(2, idProduct);
            prepareStatement.setLong(3, idClient);

            var rows = prepareStatement.executeUpdate();

            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(long idClient, long idProduct) {
        log.info("Удаляет продукт в корзине у клиента id {} продукт id {}", idClient, idProduct);

        var selectSql = """
                DELETE
                FROM ukhinms.products_baskets
                where id_product = ?
                  and id_basket = (select id from ukhinms.baskets where id_client = ? LIMIT 1)
                """;

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setLong(1, idProduct);
            prepareStatement.setLong(2, idClient);

            var rows = prepareStatement.executeUpdate();

            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BigDecimal getPrice(long idClient) {
        log.info("Считает сумму у кликента id {}", idClient);

        var selectSql = """
                select price from ukhinms.baskets where id_client = ?;
                """;

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql);) {

            prepareStatement.setLong(1, idClient);

            var resultSet = prepareStatement.executeQuery();
            if (resultSet.next()) {
                return BigDecimal.valueOf(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return BigDecimal.valueOf(0);
    }

    @Override
    public boolean isBasket(long idClient) {
        log.info("Проверяет есть ли у кликента id {} корзина", idClient);

        var selectSql = """
                SELECT EXISTS(SELECT * FROM ukhinms.products_baskets
                                join ukhinms.baskets b on b.id = products_baskets.id_basket
                                where id_client = ?)
                """;

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql);) {

            prepareStatement.setLong(1, idClient);

            var resultSet = prepareStatement.executeQuery();

            return resultSet.next() && resultSet.getBoolean(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
