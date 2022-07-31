package ru.clevertec.gordievich.domain.receipt.proxy;

import ru.clevertec.gordievich.infrastructure.exceptions.DaoException;
import ru.clevertec.gordievich.infrastructure.exceptions.NotEnoughProductsException;
import ru.clevertec.gordievich.infrastructure.exceptions.UnknownIdException;
import ru.clevertec.gordievich.domain.receipt.ReceiptService;
import ru.clevertec.gordievich.domain.receipt.ReceiptServiceImpl;

import java.lang.reflect.Proxy;

public class ReceiptServiceProxy implements ReceiptService {

    private static ReceiptService interpreterProxy;

    public ReceiptServiceProxy() {
    }

    static {
        interpreterProxy = ReceiptServiceImpl.getInstance();
        var classLoader = interpreterProxy.getClass().getClassLoader();
        var interfaces = interpreterProxy.getClass().getInterfaces();
        interpreterProxy = (ReceiptService) Proxy.newProxyInstance(classLoader, interfaces, new ReceiptServiceInvocationHandler(interpreterProxy));
    }

    @Override
    public String interpret(String[] args) throws UnknownIdException, NotEnoughProductsException, DaoException {
        return interpreterProxy.interpret(args);
    }
}
