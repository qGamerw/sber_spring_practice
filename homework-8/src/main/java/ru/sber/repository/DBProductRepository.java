package ru.sber.repository;

import org.springframework.stereotype.Repository;
import ru.sber.exception.IncorrectAmountException;
import ru.sber.model.Product;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Repository
public class DBProductRepository implements ProductRepository {
    public static final String JDBC = "jdbc:postgresql://localhost:5432/postgres?schema=<ukhinms>&user=postgres&password=postgre";

    @Override
    public long add(Product product) {
        var insertSql = "INSERT INTO ukhinms.PRODUCT (name, price) VALUES (?,?);";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setString(1, product.getName());
            prepareStatement.setDouble(2, product.getPrice().doubleValue());

            prepareStatement.executeUpdate();

            ResultSet resultSet = prepareStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                throw new RuntimeException("Ошибка при получении идентификатора");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Product> getProductById(long productId) {
        var selectSql = "SELECT * FROM ukhinms.PRODUCT where id = ?";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setLong(1, productId);

            var resultSet = prepareStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                Product product = new Product(id, name, BigDecimal.valueOf(price));

                return Optional.of(product);
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Product product) {

        var selectSql = "UPDATE ukhinms.PRODUCT SET name = ?, price = ? where id = ?;";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setString(1, product.getName());
            prepareStatement.setDouble(2, product.getPrice().doubleValue());
            prepareStatement.setLong(3, product.getId());

            var rows = prepareStatement.executeUpdate();

            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(long id) {
        var selectSql = "DELETE FROM ukhinms.PRODUCT where id = ?";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setLong(1, id);

            var rows = prepareStatement.executeUpdate();

            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getListProductName(String productName) {
        var selectSql = "SELECT * FROM ukhinms.PRODUCT where name like ?";
        List<Product> products = new ArrayList<>();

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setString(1, "%" + (productName == null ? "" : productName) + "%");

            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");

                products.add(new Product(id, name, BigDecimal.valueOf(price)));
            }

            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getListProduct() {
        var selectSql = "SELECT * FROM ukhinms.PRODUCT";
        List<Product> products = new ArrayList<>();

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {

            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");

                products.add(new Product(id, name, BigDecimal.valueOf(price)));
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
