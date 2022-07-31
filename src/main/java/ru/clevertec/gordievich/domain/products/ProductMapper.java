package ru.clevertec.gordievich.domain.products;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

public class ProductMapper implements Function<ResultSet, Product> {

    @Override
    public Product apply(ResultSet resultSet) {
        try {
            return Product.builder()
                    .id(resultSet.getInt("id"))
                    .description(resultSet.getString("description"))
                    .price(resultSet.getDouble("price"))
                    .availableNumber(resultSet.getInt("available_number"))
                    .isSpecialOffer(resultSet.getBoolean("is_special_offer"))
                    .build();
        } catch (SQLException e) {
            throw new IllegalArgumentException("Impossible to map resultSet to Product", e);
        }
    }


}
