package ru.clevertec.gordievich.api.servlet.request.product;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.clevertec.gordievich.api.servlet.handling.ServiceConsumer;
import ru.clevertec.gordievich.domain.products.Product;
import ru.clevertec.gordievich.domain.products.ProductDao;
import ru.clevertec.gordievich.infrastructure.exceptions.DaoException;
import ru.clevertec.gordievich.infrastructure.exceptions.ServiceException;

import java.io.BufferedReader;
import java.io.IOException;

import static org.apache.http.HttpStatus.*;

public class CreateProduct implements ServiceConsumer {

    private final ProductDao productDao = ProductDao.getInstance();

    @Override
    public void accept(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        try (BufferedReader bufferedReader = request.getReader()) {
            Product product = new Gson().fromJson(bufferedReader, Product.class);
            response.setStatus(productDao.createProduct(product) ? SC_CREATED : SC_BAD_REQUEST);
        } catch (IOException | DaoException e) {
            throw new ServiceException(e);
        }
    }
}
