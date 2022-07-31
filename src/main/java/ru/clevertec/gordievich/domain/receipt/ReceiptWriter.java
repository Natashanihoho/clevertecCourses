package ru.clevertec.gordievich.domain.receipt;

import java.io.IOException;

public interface ReceiptWriter {

    void writeReceipt(String receipt) throws IOException;
}
