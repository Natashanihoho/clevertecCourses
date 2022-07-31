package ru.clevertec.gordievich.api.servlet.request.product;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.clevertec.gordievich.api.servlet.handling.ServiceConsumer;
import ru.clevertec.gordievich.domain.products.ProductDao;
import ru.clevertec.gordievich.infrastructure.exceptions.DaoException;
import ru.clevertec.gordievich.infrastructure.exceptions.ServiceException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import static org.apache.http.HttpStatus.*;

public class FindAllProducts implements ServiceConsumer {

    private final ProductDao productDao = ProductDao.getInstance();

    @Override
    public void accept(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        try (PrintWriter writer = response.getWriter()) {
            Integer pageNumber = Optional.ofNullable(request.getParameter("page"))
                    .map(Integer::valueOf)
                    .orElse(1);
            String json = new Gson().toJson(productDao.findAllByPage(pageNumber));
            writer.write(json);
        } catch (IOException | DaoException e) {
            throw new ServiceException(e);
        }
    }
}
