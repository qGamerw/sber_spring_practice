package ru.sber.repository;

import org.springframework.stereotype.Repository;
import ru.sber.exception.IncorrectAmountException;
import ru.sber.model.Product;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DBBasketRepository implements BasketRepository {

    public static final String JDBC = "jdbc:postgresql://localhost:5432/postgres?schema=<ukhinms>&user=postgres&password=postgre";

    private final ProductRepository productRepository;

    public DBBasketRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public boolean add(long idClient, long idProduct, int count) {
        if (count < 1) {
            throw new IncorrectAmountException("Некорректное значение количества");
        }
        var insertSql = "INSERT INTO ukhinms.product_client (id_product, id_cart, count) VALUES (?,?,?);";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatementProdCart = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);) {
            var product = productRepository.getProductById(idProduct);

            if (product.isPresent()) {
                prepareStatementProdCart.setLong(1, idProduct);
                prepareStatementProdCart.setLong(2, getClientIdCartById(idClient));
                prepareStatementProdCart.setInt(3, count);

                prepareStatementProdCart.executeUpdate();
                return prepareStatementProdCart.getGeneratedKeys().next();
            } else {
                throw new RuntimeException("Ошибка при получении идентификатора 1");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public long getClientIdCartById(long idClient) {
        var selectSql = "SELECT * FROM ukhinms.client where id = ?";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setLong(1, idClient);

            var resultSet = prepareStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getLong(5);
            }
            throw new RuntimeException("Ошибка при получении идентификатора корзины");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(long idCart, long idProduct, int count) {
        var selectSql = "UPDATE ukhinms.product_client SET count = ? where id_product = ? and id_cart = ?;";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setDouble(1, count);
            prepareStatement.setLong(2, idProduct);
            prepareStatement.setLong(3, idCart);

            var rows = prepareStatement.executeUpdate();

            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(long idClient, long idProduct) {
        var selectSql = "DELETE FROM ukhinms.product_client where id_cart = ? and id_product = ?";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setLong(1, getClientIdCartById(idClient));
            prepareStatement.setLong(2, idProduct);

            var rows = prepareStatement.executeUpdate();

            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getListBasket() {
        return null;
    }

    @Override
    public long generateLongId() {
        return 0;
    }

    @Override
    public boolean deleteBasket(long idBasket) {
        var selectSql = "DELETE FROM ukhinms.cart where id = ?";
        var selectSqlProdClient = "DELETE FROM ukhinms.product_client where id_cart = ?";
        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql);
             var prepareStatementProdClient = connection.prepareStatement(selectSqlProdClient);) {

            prepareStatement.setLong(1, idBasket);

            prepareStatementProdClient.setLong(1, idBasket);
            prepareStatementProdClient.executeUpdate();

            var rows = prepareStatement.executeUpdate();

            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
