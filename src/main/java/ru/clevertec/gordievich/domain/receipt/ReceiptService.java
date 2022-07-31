package ru.clevertec.gordievich.domain.receipt;

import ru.clevertec.gordievich.infrastructure.exceptions.DaoException;
import ru.clevertec.gordievich.infrastructure.exceptions.NotEnoughProductsException;
import ru.clevertec.gordievich.infrastructure.exceptions.UnknownIdException;

public interface ReceiptService {

    String interpret(String[] args) throws UnknownIdException, NotEnoughProductsException, DaoException;
}
