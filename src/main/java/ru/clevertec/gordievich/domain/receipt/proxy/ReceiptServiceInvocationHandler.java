package ru.clevertec.gordievich.domain.receipt.proxy;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.clevertec.gordievich.domain.receipt.ReceiptService;
import ru.clevertec.gordievich.domain.receipt.ReceiptServiceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ReceiptServiceInvocationHandler implements InvocationHandler {

    private static final String EMPTY_STRING = "";
    private static final String INTERPRET = "interpret";

    private static final Logger logger = LoggerFactory.getLogger(ReceiptServiceImpl.class);

    private final ReceiptService receiptService;

    public ReceiptServiceInvocationHandler(ReceiptService interpreter) {
        this.receiptService = interpreter;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Gson parser = new Gson();

        Object invoke = method.invoke(receiptService, args);

        if (method.getName().equals(INTERPRET)) {
            String arguments = EMPTY_STRING;
            if (args != null) {
                arguments = parser.toJson(args);
            }
            String result = EMPTY_STRING;
            if (invoke != null) {
                //result = parser.toJson(invoke);
                result = (String) invoke;
            }

            logger.info("{} args={}", method.getName(), arguments);
            logger.info("{} result={}", method.getName(), result);
        }

        return invoke;
    }
}
