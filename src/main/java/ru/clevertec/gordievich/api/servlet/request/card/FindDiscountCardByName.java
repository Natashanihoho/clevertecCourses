package ru.clevertec.gordievich.api.servlet.request.card;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.HttpStatus;
import ru.clevertec.gordievich.api.servlet.handling.ServiceConsumer;
import ru.clevertec.gordievich.domain.cards.DiscountCard;
import ru.clevertec.gordievich.domain.cards.DiscountCardDao;
import ru.clevertec.gordievich.infrastructure.exceptions.DaoException;
import ru.clevertec.gordievich.infrastructure.exceptions.ServiceException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.BiConsumer;

import static org.apache.http.HttpStatus.*;

public class FindDiscountCardByName implements ServiceConsumer {

    private final DiscountCardDao discountCardDao = DiscountCardDao.getInstance();

    @Override
    public void accept(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        try (PrintWriter writer = response.getWriter()) {
            String id = request.getParameter("name");
            DiscountCard discountCard = discountCardDao.findByName(id).orElseThrow();
            writer.write(new Gson().toJson(discountCard));
        } catch (IOException | DaoException e) {
            throw new ServiceException(e);
        }
    }
}
