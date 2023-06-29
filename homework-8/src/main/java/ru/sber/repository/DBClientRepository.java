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
import java.util.*;

@Slf4j
@Repository
public class DBClientRepository implements ClientRepository {
    public static final String JDBC = "jdbc:postgresql://localhost:5432/postgres?schema=<ukhinms>&user=postgres&password=postgre";

    private BasketRepository basketRepository;

    public DBClientRepository(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    @Override
    public long add(Client client) {
        var insertSql = "INSERT INTO ukhinms.client (name, username, password, cart_id, email) VALUES (?,?,?,?,?);";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setString(1, client.getName());
            prepareStatement.setString(2, client.getLogin());
            prepareStatement.setString(3, client.getPassword());
            prepareStatement.setLong(4, generationIdCart());
            prepareStatement.setString(5, client.getEmail());

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

    private long generationIdCart() {
        var insertSql = "INSERT INTO ukhinms.cart (promocode) VALUES (?);";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setString(1, "");
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
        var selectSql = "SELECT * FROM ukhinms.client where id = ?";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setLong(1, idClient);

            var resultSet = prepareStatement.executeQuery();

            if (resultSet.next()) {
                long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String login = resultSet.getString(3);
                String password = resultSet.getString(4);
                String email = resultSet.getString(5);
                long card = resultSet.getLong(6);

                var b = new Basket(idClient, getClientProduct(card), 0);

                var client = new Client(id, name, login, password, email, card, b);
                return Optional.of(client);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Product> getClientProduct(long idCard) {
        var selectSql = """
                SELECT p.id, p.name, p.price, pc.count FROM ukhinms.product_client pc
                    join ukhinms.PRODUCT p on pc.id_product=p.id
                    join ukhinms.client c on c.cart_id=pc.id_cart
                                             where c.cart_id =?
                """;
        List<Product> products = new ArrayList<>();

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql);) {

            prepareStatement.setLong(1, idCard);

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

        var selectSql = "SELECT cart_id FROM ukhinms.client where id = ?";
        var selectSqlClient = "DELETE FROM ukhinms.client where id = ?";

        try (var connection = DriverManager.getConnection(JDBC);

             var prepareStatementBasket = connection.prepareStatement(selectSql);

             var prepareStatementClient = connection.prepareStatement(selectSqlClient);) {
            prepareStatementBasket.setLong(1, id);

            var resultSet = prepareStatementBasket.executeQuery();

            if (resultSet.next()) {
                log.info("select {}", resultSet.getLong("cart_id"));
                prepareStatementClient.setLong(1, id);
                var rows = prepareStatementClient.executeUpdate();

                var idBasket = basketRepository.
                        deleteBasket(resultSet.getLong("cart_id"));


                return (rows > 0) && idBasket;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
