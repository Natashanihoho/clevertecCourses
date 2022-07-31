package ru.clevertec.gordievich;

import ru.clevertec.gordievich.domain.receipt.ReceiptServiceImpl;
import ru.clevertec.gordievich.domain.receipt.ReceiptWriter;
import ru.clevertec.gordievich.infrastructure.exceptions.DaoException;
import ru.clevertec.gordievich.infrastructure.exceptions.NotEnoughProductsException;
import ru.clevertec.gordievich.infrastructure.exceptions.UnknownIdException;
import ru.clevertec.gordievich.infrastructure.connection.PropertiesUtil;

import java.io.*;

public class JdbcRunner {

    public static void main(String[] args) throws UnknownIdException, NotEnoughProductsException, IOException, DaoException {
        String[] fileArgs;
        try(BufferedReader reader = new BufferedReader(new FileReader(PropertiesUtil.get("ORDER_FILE_PATH")))) {
            String argsLine = reader.readLine();
            fileArgs = argsLine.split(" ");
        }
        ReceiptServiceImpl receiptServiceImpl = ReceiptServiceImpl.getInstance();
        String receipt = receiptServiceImpl.interpret(fileArgs);
        ReceiptWriter receiptWriter = rec -> {
            try(FileWriter fileWriter = new FileWriter(PropertiesUtil.get("RECEIPT_FILE_PATH"))) {
                fileWriter.write(receipt);
            }
        };

        receiptWriter.writeReceipt(receipt);
    }
}
