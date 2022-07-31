package ru.clevertec.gordievich.api.servlet.handling;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.clevertec.gordievich.infrastructure.exceptions.ServiceException;

@FunctionalInterface
public interface ServiceConsumer {

    void accept(HttpServletRequest request, HttpServletResponse response) throws ServiceException;

}
