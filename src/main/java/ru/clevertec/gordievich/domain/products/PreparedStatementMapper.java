package ru.clevertec.gordievich.domain.products;


import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PreparedStatementMapper {

    public void accept(PreparedStatement preparedStatement, Product product) throws SQLException {
        preparedStatement.setString(1, product.getDescription());
        preparedStatement.setDouble(2, product.getPrice());
        preparedStatement.setInt(3, product.getAvailableNumber());
        preparedStatement.setBoolean(4, product.isSpecialOffer());
        preparedStatement.setInt(5, product.getId());
    }

}
