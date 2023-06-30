package ru.sber.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.sber.model.Basket;
import ru.sber.model.Client;
import ru.sber.model.Product;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class DBClientRepository implements ClientRepository {
    public static final String JDBC = "jdbc:postgresql://localhost:5432/postgres?schema=<ukhinms>&user=postgres&password=postgre";

    @Override
    public long add(Client client) {
        log.info("Создает клиента базе данных {}", client);

        var insertSql = "INSERT INTO ukhinms.clients (name, email, id_card, username, password) VALUES (?,?,?,?,?);";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {

            prepareStatement.setString(1, client.getName());
            prepareStatement.setString(2, client.getEmail());
            prepareStatement.setLong(3, client.getCard());
            prepareStatement.setString(4, client.getLogin());
            prepareStatement.setString(5, client.getPassword());

            prepareStatement.executeUpdate();

            ResultSet resultSet = prepareStatement.getGeneratedKeys();
            if (resultSet.next()) {
                generationIdCart(resultSet.getLong(1));
                return resultSet.getLong(1);
            } else {
                throw new RuntimeException("Ошибка при получении идентификатора");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private long generationIdCart(long idClient) {
        log.info("Создает корзину в базе данных с id клиентом {}", idClient);

        var insertSql = "INSERT INTO ukhinms.baskets (id_client, price, promo_code) VALUES (?,?,?);";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setLong(1, idClient);
            prepareStatement.setLong(2, 0);
            prepareStatement.setString(3, "");

            prepareStatement.executeUpdate();

            ResultSet resultSet = prepareStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            } else {
                throw new RuntimeException("Ошибка при получении идентификатора");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Client> getClientById(long idClient) {
        log.info("Получает информации о клиенте с id {}", idClient);

        var selectSql = "SELECT * FROM ukhinms.clients where id = ?";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setLong(1, idClient);

            var resultSet = prepareStatement.executeQuery();

            if (resultSet.next()) {
                long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                long card = resultSet.getLong(4);
                String login = resultSet.getString(5);
                String password = resultSet.getString(6);

                var b = new Basket(idClient, getClientProduct(id), 0);

                return Optional.of(new Client(id, name, email, card, login, password, b));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Product> getClientProduct(long idClient) {
        log.info("Получает информацию и пролуктов у клиента {}", idClient);

        var selectSql = """
                SELECT p.id, p.name, p.price, pc.count
                FROM ukhinms.products_baskets pc
                    join ukhinms.PRODUCTs p on pc.id_product = p.id
                    join ukhinms.baskets b on b.id = pc.id_basket
                where b.id_client = ?
                """;

        List<Product> products = new ArrayList<>();

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql);) {

            prepareStatement.setLong(1, idClient);

            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                var id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                double price = resultSet.getDouble(3);
                int count = resultSet.getInt(4);

                products.add(new Product(id, name, BigDecimal.valueOf(price), count));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    @Override
    public boolean deleteById(long id) {
        log.info("Удаляет клиента с id {}", id);

        var selectSql = "delete from ukhinms.clients where id = ?";

        try (var connection = DriverManager.getConnection(JDBC);

             var preparedStatement = connection.prepareStatement(selectSql)) {
            preparedStatement.setLong(1, id);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
