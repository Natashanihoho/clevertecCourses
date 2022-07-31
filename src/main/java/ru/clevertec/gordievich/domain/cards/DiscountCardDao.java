package ru.clevertec.gordievich.domain.cards;

import lombok.NoArgsConstructor;
import ru.clevertec.gordievich.infrastructure.exceptions.DaoException;
import ru.clevertec.gordievich.infrastructure.connection.ConnectionManager;

import java.sql.SQLException;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class DiscountCardDao {

    private static final DiscountCardDao INSTANCE = new DiscountCardDao();
    private static final DiscountCardMapper mapper = new DiscountCardMapper();

    private static final String SQL_CREATE_DISCOUNT_CARD = """
            INSERT INTO discount_card (card_name, discount_percent)
            VALUES (?, ?)            
            """;

    private static final String SQL_FIND_BY_NAME = """
            SELECT card_name, discount_percent
            FROM discount_card
            WHERE card_name = ?
            """;

    private static final String SQL_UPDATE = """
            UPDATE discount_card 
            SET discount_percent = ?
            WHERE card_name = ?
            """;

    private static final String SQL_DELETE_BY_ID = """
            DELETE FROM discount_card
            WHERE card_name = ?
            """;

    public boolean createDiscountCard(DiscountCard discountCard) throws DaoException {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SQL_CREATE_DISCOUNT_CARD)) {
            preparedStatement.setString(1, discountCard.getCardName());
            preparedStatement.setInt(2, discountCard.getDiscountPercent());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException("DiscountCardDao exception: createDiscountCard", e);
        }
    }

    public Optional<DiscountCard> findByName(String name) throws DaoException {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SQL_FIND_BY_NAME)) {
            preparedStatement.setString(1, name);
            var resultSet = preparedStatement.executeQuery();
            return Optional.ofNullable(resultSet.next() ? mapper.apply(resultSet) : null);
        } catch (SQLException e) {
            throw new DaoException("DiscountCardDao exception: findByName", e);
        }
    }

    public boolean update(DiscountCard discountCard) throws DaoException {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setInt(1, discountCard.getDiscountPercent());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException("DiscountCardDao exception: update", e);
        }
    }

    public boolean deleteByName(String name) throws DaoException {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ID)) {
            preparedStatement.setString(1, name);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException("DiscountCardDao exception: deleteById", e);
        }
    }

    public static DiscountCardDao getInstance() {
        return INSTANCE;
    }
}
