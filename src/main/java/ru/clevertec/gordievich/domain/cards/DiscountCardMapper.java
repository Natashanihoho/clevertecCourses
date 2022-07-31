package ru.clevertec.gordievich.domain.cards;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

public class DiscountCardMapper implements Function<ResultSet, DiscountCard> {
    @Override
    public DiscountCard apply(ResultSet resultSet) {
        try {
            return DiscountCard.builder()
                    .cardName(resultSet.getString("card_name"))
                    .discountPercent(resultSet.getInt("discount_percent"))
                    .build();
        } catch (SQLException e) {
            throw new IllegalArgumentException("Impossible to map resultSet to DiscountCard", e);
        }
    }
}
