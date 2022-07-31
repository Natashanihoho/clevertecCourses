package ru.clevertec.gordievich.api.servlet.handling;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.clevertec.gordievich.api.servlet.request.card.CreateDiscountCard;
import ru.clevertec.gordievich.api.servlet.request.card.DeleteDiscountCard;
import ru.clevertec.gordievich.api.servlet.request.card.FindDiscountCardByName;
import ru.clevertec.gordievich.api.servlet.request.card.UpdateDiscountCard;
import ru.clevertec.gordievich.api.servlet.request.product.*;
import ru.clevertec.gordievich.api.servlet.request.receipt.ReceiptPdf;
import ru.clevertec.gordievich.infrastructure.exceptions.ExceptionInfo;
import ru.clevertec.gordievich.infrastructure.exceptions.ServiceException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.function.BiConsumer;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static ru.clevertec.gordievich.api.servlet.handling.HttpMethod.*;

@RequiredArgsConstructor
@Getter
public enum CommandProvider implements BiConsumer<HttpServletRequest, HttpServletResponse> {

    PRODUCT_GET("product_by_id", GET,new FindProductById()),
    PRODUCT_POST("product_create", POST, new CreateProduct()),
    PRODUCT_DELETE("product_delete", DELETE, new DeleteProduct()),
    PRODUCT_GET_ALL("product_all", GET, new FindAllProducts()),
    PRODUCT_UPDATE("product_update", PUT, new UpdateProduct()),

    DISCOUNT_CARD_GET("discount_card_by_name", GET, new FindDiscountCardByName()),
    DISCOUNT_CARD_POST("discount_card_create", POST, new CreateDiscountCard()),
    DISCOUNT_CARD_DELETE("discount_card_delete", DELETE, new DeleteDiscountCard()),
    DISCOUNT_CARD_UPDATE("discount_card_update", PUT, new UpdateDiscountCard()),

    RECEIPT_GET("receipt", GET, new ReceiptPdf());

    private final String endpoint;
    private final HttpMethod httpMethod;
    private final ServiceConsumer router;

    @Override
    public void accept(HttpServletRequest request, HttpServletResponse response) {
        try {
            router.accept(request, response);
        } catch (ServiceException e) {
            response.setStatus(SC_BAD_REQUEST);
            try (PrintWriter printWriter = response.getWriter()) {
                ExceptionInfo exceptionInfo = ExceptionInfo.builder()
                        .message(e.getMessage())
                        .stackTrace(
                                Arrays.stream(e.getStackTrace())
                                        .map(StackTraceElement::toString)
                                        .toArray(String[]::new)
                        ).build();
                printWriter.write(new Gson().toJson(exceptionInfo));
            } catch (IOException ioException) {
                response.setStatus(SC_BAD_REQUEST);
            }
        }
    }

    public static BiConsumer<HttpServletRequest, HttpServletResponse> byEndpointAndMethod(String endpoint, String method) {
        return Arrays.stream(values())
                .filter(command -> command.endpoint.equals(endpoint))
                .filter(command -> command.httpMethod.name().equals(method))
                .findFirst()
                .orElseThrow();
    }
}
