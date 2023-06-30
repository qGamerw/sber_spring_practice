package ru.sber.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.sber.model.Basket;
import ru.sber.model.Client;
import ru.sber.model.Product;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

@Slf4j
@Repository
public class DBClientRepository implements ClientRepository {

    private final JdbcTemplate jdbcTemplate;

    public DBClientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long add(Client client) {
        log.info("Создает клиента базе данных {}", client);

        var insertClientSQL = "INSERT INTO clients (name, email, id_card, username, password) VALUES (?,?,?,?,?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var preparedStatement = connection.prepareStatement(insertClientSQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getEmail());
            preparedStatement.setLong(3, client.getCard());
            preparedStatement.setString(4, client.getLogin());
            preparedStatement.setString(5, client.getPassword());

            return preparedStatement;
        };

        jdbcTemplate.update(preparedStatementCreator, keyHolder);

        long id = (long) (int) keyHolder.getKeys().get("id");
        generationIdCart(id);

        return id;
    }

    private long generationIdCart(long idClient) {
        log.info("Создает корзину в базе данных с id клиентом {}", idClient);

        var insertBasketSQL = "INSERT INTO baskets (id_client, price, promo_code) VALUES (?,?,?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var preparedStatement = connection.prepareStatement(insertBasketSQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, idClient);
            preparedStatement.setLong(2, 0);
            preparedStatement.setString(3, "");

            return preparedStatement;
        };

        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return (long) (int) keyHolder.getKeys().get("id");
    }

    @Override
    public Optional<Client> getClientById(long idClient) {
        log.info("Получает информации о клиенте с id {}", idClient);

        var selectClientSQL = "SELECT * FROM clients where id = ?";

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(selectClientSQL);
            prepareStatement.setLong(1, idClient);

            return prepareStatement;
        };

        RowMapper<Client> rowMapper = (resultSet, Numrow) -> {
            long id = resultSet.getLong(1);
            String name = resultSet.getString(2);
            String email = resultSet.getString(3);
            long card = resultSet.getLong(4);
            String login = resultSet.getString(5);
            String password = resultSet.getString(6);

            var basket = new Basket(idClient, getClientProduct(id), 0);

            return new Client(id, name, email, card, login, password, basket);
        };

        List<Client> clients = jdbcTemplate.query(preparedStatementCreator, rowMapper);
        return clients.stream().findFirst();
    }

    private List<Product> getClientProduct(long idClient) {
        log.info("Получает информацию и продуктов у клиента {}", idClient);

        var selectClientSQL = """
                SELECT p.id, p.name, p.price, pc.count
                FROM products_baskets pc
                    join PRODUCTs p on pc.id_product = p.id
                    join baskets b on b.id = pc.id_basket
                where b.id_client = ?
                """;

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var preparedStatement = connection.prepareStatement(selectClientSQL);
            preparedStatement.setLong(1, idClient);

            return preparedStatement;
        };

        RowMapper<Product> rowMapper = (resultSet, rowNum) -> {
            var id = resultSet.getLong(1);
            String name = resultSet.getString(2);
            double price = resultSet.getDouble(3);
            int count = resultSet.getInt(4);

            return new Product(id, name, BigDecimal.valueOf(price), count);
        };

        return jdbcTemplate.query(preparedStatementCreator, rowMapper);
    }

    @Override
    public boolean deleteById(long id) {
        log.info("Удаляет клиента с id {}", id);

        var deleteClientSQL = "delete from clients where id = ?";

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var preparedStatement = connection.prepareStatement(deleteClientSQL);
            preparedStatement.setLong(1, id);

            return preparedStatement;
        };

        int rows = jdbcTemplate.update(preparedStatementCreator);
        return rows > 0;
    }
}
