package ru.clevertec.gordievich.api.servlet.request.card;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.clevertec.gordievich.api.servlet.handling.ServiceConsumer;
import ru.clevertec.gordievich.domain.cards.DiscountCard;
import ru.clevertec.gordievich.domain.cards.DiscountCardDao;
import ru.clevertec.gordievich.infrastructure.exceptions.DaoException;
import ru.clevertec.gordievich.infrastructure.exceptions.ServiceException;


import java.io.BufferedReader;
import java.io.IOException;

import static org.apache.http.HttpStatus.*;

public class CreateDiscountCard implements ServiceConsumer {

    private final DiscountCardDao discountCardDao = DiscountCardDao.getInstance();

    @Override
    public void accept(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        try (BufferedReader bufferedReader = request.getReader()) {
            DiscountCard discountCard = new Gson().fromJson(bufferedReader, DiscountCard.class);
            response.setStatus(discountCardDao.createDiscountCard(discountCard) ? SC_CREATED : SC_BAD_REQUEST);
        } catch (IOException | DaoException e) {
            throw new ServiceException(e);
        }
    }
}
