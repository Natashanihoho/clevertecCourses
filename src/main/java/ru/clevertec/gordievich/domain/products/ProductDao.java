package ru.clevertec.gordievich.domain.products;


import lombok.NoArgsConstructor;
import ru.clevertec.gordievich.infrastructure.exceptions.DaoException;
import ru.clevertec.gordievich.infrastructure.connection.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ProductDao {

    private static final ProductDao INSTANCE = new ProductDao();
    private static final Function<ResultSet, Product> mapper = new ProductMapper();
    private static final PreparedStatementMapper preparedStatementMapper = new PreparedStatementMapper();

    private static final String SQL_CREATE_PRODUCT = """
            INSERT INTO product (id, description, price, available_number, is_special_offer)  
            VALUES (?, ?, ?, ?, ?)            
            """;

    private static final String SQL_FIND_BY_ID = """
            SELECT id, description, price, available_number, is_special_offer 
            FROM product
            WHERE id = ?
            """;

    private static final String SQL_FIND_ALL_BY_PAGE = """
            SELECT id, description, price, available_number, is_special_offer 
            FROM product   
            OFFSET ?  
            LIMIT 10     
            """;

    private static final String SQL_UPDATE = """
            UPDATE product 
            SET description = ?,
                price = ?,
                available_number = ?,
                is_special_offer = ?
            WHERE id = ?
            """;

    private static final String SQL_DELETE_BY_ID = """
            DELETE FROM product
            WHERE id = ?
            """;

    public boolean createProduct(Product product) throws DaoException {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SQL_CREATE_PRODUCT)) {
            preparedStatementMapper.accept(preparedStatement,product);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException("ProductDao exception: createProduct", e);
        }
    }

    public Optional<Product> findById(int id) throws DaoException {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SQL_FIND_BY_ID)) {
            preparedStatement.setInt(1, id);
            var resultSet = preparedStatement.executeQuery();
            return Optional.ofNullable(resultSet.next() ? mapper.apply(resultSet) : null);
        } catch (SQLException e) {
            throw new DaoException("ProductDao exception: findById", e);
        }
    }

    public List<Product> findAllByPage(int page) throws DaoException {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SQL_FIND_ALL_BY_PAGE)) {
            List<Product> products = new ArrayList<>();
            preparedStatement.setInt(1, 10 * (page - 1));
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                products.add(mapper.apply(resultSet));
            }
            return products;
        } catch (SQLException e) {
            throw new DaoException("ProductDao exception: findById", e);
        }
    }

    public boolean update(Product product) throws DaoException {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
            preparedStatementMapper.accept(preparedStatement,product);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException("ProductDao exception: update", e);
        }
    }

    public boolean deleteById(int id) throws DaoException {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ID)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException("ProductDao exception: deleteById", e);
        }
    }

    public static ProductDao getInstance() {
        return INSTANCE;
    }
}
