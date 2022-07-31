package ru.clevertec.gordievich.api.servlet.request.product;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.clevertec.gordievich.api.servlet.handling.ServiceConsumer;
import ru.clevertec.gordievich.domain.products.ProductDao;
import ru.clevertec.gordievich.infrastructure.exceptions.DaoException;
import ru.clevertec.gordievich.infrastructure.exceptions.ServiceException;

import static org.apache.http.HttpStatus.*;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;

public class DeleteProduct implements ServiceConsumer {

    private final ProductDao productDao = ProductDao.getInstance();

    @Override
    public void accept(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        try {
            String id = request.getParameter("id");
            boolean deleteResult = productDao.deleteById(Integer.parseInt(id));
            response.setStatus(deleteResult ? SC_NO_CONTENT : SC_BAD_REQUEST);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
