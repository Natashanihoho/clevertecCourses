package ru.clevertec.gordievich.api.servlet.request.card;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.HttpStatus;
import ru.clevertec.gordievich.api.servlet.handling.ServiceConsumer;
import ru.clevertec.gordievich.domain.cards.DiscountCardDao;
import ru.clevertec.gordievich.infrastructure.exceptions.DaoException;
import ru.clevertec.gordievich.infrastructure.exceptions.ServiceException;

import java.util.function.BiConsumer;

import static jakarta.servlet.http.HttpServletResponse.*;
import static org.apache.http.HttpStatus.*;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;

public class DeleteDiscountCard implements ServiceConsumer {

    private final DiscountCardDao discountCardDao = DiscountCardDao.getInstance();

    @Override
    public void accept(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        try {
            String id = request.getParameter("name");
            boolean deleteResult = discountCardDao.deleteByName(id);
            response.setStatus(deleteResult ? SC_NO_CONTENT : SC_BAD_REQUEST);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
