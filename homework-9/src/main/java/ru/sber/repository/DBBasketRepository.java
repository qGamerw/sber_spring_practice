package ru.sber.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.sber.exception.IncorrectAmountException;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

/**
 * Класс для взаимодействия с корзиной
 */
@Slf4j
@Repository
public class DBBasketRepository implements BasketRepository {

    private final JdbcTemplate jdbcTemplate;

    public DBBasketRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean add(long idClient, long idProduct, int count) {
        log.info("Добавляет продукт в корзину клиента с id {} продукт id {} в количестве {}",
                idClient, idProduct, count);

        if (count < 1) {
            throw new IncorrectAmountException("Некорректное значение количества");
        }

        var insertProductSQL = """
                INSERT INTO products_baskets (id_product, id_basket, count)
                VALUES (?, (select id from baskets where id_client = ? LIMIT 1), ?);
                """;
        var selectExistsProductSQL = """
                SELECT EXISTS(SELECT * FROM products where id = ?)
                """;

        var selectExistBasketSQL = """
                SELECT EXISTS(SELECT *
                              FROM products_baskets pb
                                       join products p on p.id = pb.id_product
                              where id_product = ?)
                """;

        PreparedStatementCreator preparedStatementCreatorExistsProduct = connection -> {
            var preparedStatement = connection.prepareStatement(selectExistsProductSQL);
            preparedStatement.setLong(1, idProduct);

            return preparedStatement;
        };

        PreparedStatementCreator preparedStatementCreatorExistBasket = connection -> {
            var preparedStatement = connection.prepareStatement(selectExistBasketSQL);
            preparedStatement.setLong(1, idProduct);

            return preparedStatement;
        };

        PreparedStatementCreator preparedStatementCreatorInsertProduct = connection -> {
            var preparedStatement = connection.prepareStatement(insertProductSQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, idProduct);
            preparedStatement.setLong(2, idClient);
            preparedStatement.setInt(3, count);

            return preparedStatement;
        };

        RowMapper<Boolean> rowMapper = (resultSet, rowNum) -> resultSet.getBoolean(1);

        var isExistsProduct = jdbcTemplate.query(preparedStatementCreatorExistsProduct, rowMapper)
                .stream()
                .findFirst()
                .get();

        var isExistsBasket = jdbcTemplate.query(preparedStatementCreatorExistBasket, rowMapper)
                .stream()
                .findFirst()
                .get();

        if (isExistsProduct && !isExistsBasket) {

            int rows = jdbcTemplate.update(preparedStatementCreatorInsertProduct);
            updatePriceBasket(idClient);
            return rows > 0;

        } else if (isExistsProduct) {

            return UpgradeExistingProduct(idClient, idProduct, count);
        }
        return false;
    }

    @Override
    public boolean update(long idClient, long idProduct, int count) {
        log.info("Меняет количество продуктов в корзине у клиента id {} продукта id {} на {}",
                idClient, idProduct, count);

        var updateProductCount = """
                UPDATE products_baskets
                SET count = ?
                WHERE id_product = ?
                AND id_basket = (SELECT id FROM baskets WHERE id_client = ? LIMIT 1)
                """;

        return PreparedStatementCreatorUpdate(idClient, idProduct, count, updateProductCount);
    }

    @Override
    public boolean delete(long idClient, long idProduct) {
        log.info("Удаляет продукт в корзине у клиента id {} продукт id {}", idClient, idProduct);

        var DeleteProductSQL = """
                DELETE
                FROM products_baskets
                WHERE id_product = ?
                AND id_basket = (SELECT id FROM baskets WHERE id_client = ? LIMIT 1)
                """;

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var preparedStatement = connection.prepareStatement(DeleteProductSQL);
            preparedStatement.setLong(1, idProduct);
            preparedStatement.setLong(2, idClient);

            return preparedStatement;
        };

        int rows = jdbcTemplate.update(preparedStatementCreator);
        updatePriceBasket(idClient);

        return rows > 0;
    }

    @Override
    public BigDecimal getPrice(long idClient) {
        log.info("Считает сумму у клиента id {}", idClient);

        var selectPriceSQL = """
                SELECT CEIL(b.price - (b.price * pc.discount))
                FROM baskets b
                join promo_codes pc on pc.id = b.id_promo_code
                WHERE id_client = ?;
                """;

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var preparedStatement = connection.prepareStatement(selectPriceSQL);
            preparedStatement.setLong(1, idClient);

            return preparedStatement;
        };

        RowMapper<BigDecimal> rowMapper = (resultSet, rowNum) -> resultSet.getBigDecimal(1);

        return jdbcTemplate.query(preparedStatementCreator, rowMapper)
                .stream()
                .findFirst()
                .get();
    }

    @Override
    public boolean isBasket(long idClient) {
        log.info("Проверяет есть ли у клиента id {} корзина", idClient);

        var selectExistsBasketSQL = """
                SELECT EXISTS(SELECT * FROM products_baskets
                                  JOIN baskets b ON b.id = products_baskets.id_basket
                WHERE id_client = ?)
                """;

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var preparedStatement = connection.prepareStatement(selectExistsBasketSQL);
            preparedStatement.setLong(1, idClient);

            return preparedStatement;
        };

        RowMapper<Boolean> rowMapper = (resultSet, rowNum) -> resultSet.getBoolean(1);

        List<Boolean> getPrice = jdbcTemplate.query(preparedStatementCreator, rowMapper);

        return getPrice.stream()
                .findFirst()
                .get();
    }

    @Override
    public boolean isCountProduct(long idClient) {
        log.info("Проверяет хватает ли товара для оплаты");

        var selectExistsPoduct = """
                SELECT EXISTS (SELECT (p.amount - pb.count)
                               FROM products_baskets pb
                                        JOIN products AS p ON p.id = pb.id_product
                               WHERE (p.amount - pb.count) < 0);
                """;

        RowMapper<Boolean> rowMapper = (resultSet, rowNum) -> resultSet.getBoolean(1);

        List<Boolean> res = jdbcTemplate.query(selectExistsPoduct, rowMapper);
        return res.stream().
                findFirst()
                .get();
    }

    @Override
    public boolean removeProductBasket(long idClient) {
        log.info("Удаляет товары на складе и корзины");

        var updateProductMinusSQL = """
                UPDATE products
                SET amount = amount - products_baskets.count
                FROM products_baskets
                WHERE products.id = products_baskets.id_product
                """;

        var deleteProductBasketSQL = """
                DELETE from products_baskets where id_basket = ?;
                """;

        var updateBasketSQL = """
                UPDATE baskets SET price = 0 where id = ?;
                """;

        PreparedStatementCreator preparedStatementCreatorUpdateProduce = connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(updateBasketSQL);
            preparedStatement.setLong(1, idClient);

            return preparedStatement;
        };

        PreparedStatementCreator preparedStatementCreatorDeleteClient = connection -> {
            var preparedStatement = connection.prepareStatement(deleteProductBasketSQL);
            preparedStatement.setLong(1, idClient);

            return preparedStatement;
        };

        int rowsDeleteUpdateBasket = jdbcTemplate.update(preparedStatementCreatorUpdateProduce);
        int rowsUpdateProducts = jdbcTemplate.update(updateProductMinusSQL);
        int rowsDeleteProductBasket = jdbcTemplate.update(preparedStatementCreatorDeleteClient);

        return rowsUpdateProducts > 0 && rowsDeleteProductBasket > 0 && rowsDeleteUpdateBasket > 0;
    }

    @Override
    public Optional<Long> getIdCard(long idClient) {
        log.info("Получение id карты клиента {}", idClient);

        var selectIdCardSql = """
                SELECT id_card FROM clients where id = ?;
                """;

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var preparedStatement = connection.prepareStatement(selectIdCardSql);
            preparedStatement.setLong(1, idClient);

            return preparedStatement;
        };

        RowMapper<Long> rowMapper = (resultSet, rowNum) -> resultSet.getLong(1);

        return jdbcTemplate.query(preparedStatementCreator, rowMapper)
                .stream()
                .findFirst();
    }

    private boolean UpgradeExistingProduct(long idClient, long idProduct, int count) {
        var updateProductCount = """
                UPDATE products_baskets
                SET count = count + ?
                WHERE id_product = ?
                AND id_basket = (SELECT id FROM baskets WHERE id_client = ? LIMIT 1)
                """;

        return PreparedStatementCreatorUpdate(idClient, idProduct, count, updateProductCount);
    }

    private boolean PreparedStatementCreatorUpdate(long idClient,
                                                   long idProduct,
                                                   int count,
                                                   String updateProductCount) {

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var preparedStatement = connection.prepareStatement(updateProductCount);
            preparedStatement.setDouble(1, count);
            preparedStatement.setLong(2, idProduct);
            preparedStatement.setLong(3, idClient);

            return preparedStatement;
        };

        int rows = jdbcTemplate.update(preparedStatementCreator);
        updatePriceBasket(idClient);

        return rows > 0;
    }

    private void updatePriceBasket(long idClient) {
        log.info("Обновляет цену у клиента id {} в корзине", idClient);

        var updateBasketSQL = """
                UPDATE baskets
                SET price = (SELECT SUM(p.price * pb.count)
                             FROM products p
                             JOIN products_baskets pb ON pb.id_product = p.id
                             WHERE pb.id_basket = ?)
                WHERE id = ?;
                """;

        PreparedStatementCreator preparedStatementCreator2 = connection -> {
            var preparedStatement = connection.prepareStatement(updateBasketSQL);
            preparedStatement.setLong(1, idClient);
            preparedStatement.setLong(2, idClient);

            return preparedStatement;
        };

        jdbcTemplate.update(preparedStatementCreator2);
    }
}
