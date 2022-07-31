package ru.clevertec.gordievich.api.servlet.request.product;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.clevertec.gordievich.api.servlet.handling.ServiceConsumer;
import ru.clevertec.gordievich.domain.products.Product;
import ru.clevertec.gordievich.domain.products.ProductDao;
import ru.clevertec.gordievich.infrastructure.exceptions.DaoException;
import ru.clevertec.gordievich.infrastructure.exceptions.ServiceException;

import java.io.IOException;
import java.io.PrintWriter;

public class FindProductById implements ServiceConsumer {

    private final ProductDao productDao = ProductDao.getInstance();

    @Override
    public void accept(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        try (PrintWriter writer = response.getWriter()) {
            String id = request.getParameter("id");
            Product product = productDao.findById(Integer.parseInt(id)).orElseThrow();
            writer.write(new Gson().toJson(product));
        } catch (IOException | DaoException e) {
            throw new ServiceException(e);
        }
    }
}
