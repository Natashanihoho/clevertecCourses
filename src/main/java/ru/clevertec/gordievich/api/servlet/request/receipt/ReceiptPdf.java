package ru.clevertec.gordievich.api.servlet.request.receipt;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.Property;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.clevertec.gordievich.api.servlet.handling.ServiceConsumer;
import ru.clevertec.gordievich.domain.receipt.ReceiptService;
import ru.clevertec.gordievich.domain.receipt.ReceiptServiceImpl;
import ru.clevertec.gordievich.infrastructure.exceptions.DaoException;
import ru.clevertec.gordievich.infrastructure.exceptions.NotEnoughProductsException;
import ru.clevertec.gordievich.infrastructure.exceptions.ServiceException;
import ru.clevertec.gordievich.infrastructure.exceptions.UnknownIdException;


import java.io.*;

public class ReceiptPdf implements ServiceConsumer {

    private final ReceiptService receiptService = ReceiptServiceImpl.getInstance();

    @Override
    public void accept(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        try (OutputStream outputStream = response.getOutputStream()) {
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "inline; filename=\"receipt.pdf\"");
            String[] values = request.getParameterValues("value");
            String receipt = receiptService.interpret(values);
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outputStream));
            Document doc = new Document(pdfDoc);
            Text text = new Text(receipt);
            Paragraph para1 = new Paragraph(text);
            doc.add(para1);
            doc.close();
        } catch (IOException | UnknownIdException | NotEnoughProductsException | DaoException e) {
            throw new ServiceException(e);
        }
    }
}
