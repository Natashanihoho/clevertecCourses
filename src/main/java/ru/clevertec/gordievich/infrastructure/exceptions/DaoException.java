package ru.clevertec.gordievich.infrastructure.exceptions;

public class DaoException extends Exception {

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
