package ru.sber.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.sber.exception.IncorrectAmountException;
import ru.sber.model.Product;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class DBProductRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public DBProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long add(Product product) {
        log.info("Добавляет товар в базу данных {}", product);

        if (product.getCount() < 1) {
            throw new IncorrectAmountException("Некорректное значение количества");
        }

        var insertProductSql = "INSERT INTO PRODUCTS (name, price, amount) VALUES (?,?,?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var preparedStatement = connection.prepareStatement(insertProductSql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice().doubleValue());
            preparedStatement.setInt(3, product.getCount());

            return preparedStatement;
        };

        jdbcTemplate.update(preparedStatementCreator, keyHolder);

        return (long) (int) keyHolder.getKeys().get("id");
    }

    @Override
    public Optional<Product> getProductById(long idProduct) {
        log.info("Получает товар с id {}", idProduct);

        var selectProductSQL = "SELECT * FROM PRODUCTS where id = ?";

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var preparedStatement = connection.prepareStatement(selectProductSQL);
            preparedStatement.setLong(1, idProduct);

            return preparedStatement;
        };

        RowMapper<Product> productRowMapper = (resultSet, rowNum) -> {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            double price = resultSet.getDouble("price");
            int amount = resultSet.getInt("amount");

           return new Product(id, name, BigDecimal.valueOf(price), amount);
        };

        List<Product> products = jdbcTemplate.query(preparedStatementCreator, productRowMapper);
        return products.stream().findFirst();
    }

    @Override
    public boolean update(Product product) {
        log.info("Обновляет информацию в базе данных по продукту {}", product);

        var updateProductSQL = "UPDATE PRODUCTS SET name = ?, price = ?, amount = ? where id = ?;";

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var preparedStatement = connection.prepareStatement(updateProductSQL);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice().doubleValue());
            preparedStatement.setLong(3, product.getCount());
            preparedStatement.setLong(4, product.getIdProduct());

            return preparedStatement;
        };

        int rows = jdbcTemplate.update(preparedStatementCreator);
        return rows > 0;
    }

    @Override
    public boolean delete(long id) {
        log.info("Удаляет продукт с id {}", id);

        var deleteProductSQL = "DELETE FROM PRODUCTS where id = ?";

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var preparedStatement = connection.prepareStatement(deleteProductSQL);
            preparedStatement.setLong(1, id);

            return preparedStatement;
        };

        int rows = jdbcTemplate.update(preparedStatementCreator);
        return rows > 0;
    }

    @Override
    public List<Product> getListProductName(String productName) {
        log.info("Получает продукт с именем {}", productName);

        var selectProductSQL = "SELECT * FROM PRODUCTS where name like ?";

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var preparedStatement = connection.prepareStatement(selectProductSQL);
            preparedStatement.setString(1, "%" + (productName == null ? "" : productName) + "%");

            return preparedStatement;
        };

        RowMapper<Product> rowMapper = (resultSet, rowNum) -> {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            double price = resultSet.getDouble("price");
            int amount = resultSet.getInt("amount");

            return new Product(id, name, BigDecimal.valueOf(price), amount);
        };

        return jdbcTemplate.query(preparedStatementCreator, rowMapper);
    }
}
