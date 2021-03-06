package ru.clevertec.gordievich.web;

import ru.clevertec.gordievich.exceptions.InvalidDataFormat;
import ru.clevertec.gordievich.exceptions.NotEnoughProductsException;
import ru.clevertec.gordievich.exceptions.UnknownIdException;
import ru.clevertec.gordievich.service.Interpreter;
import ru.clevertec.gordievich.service.InterpreterImpl;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.URI;

import java.nio.charset.StandardCharsets;

public class Controller {

    public void execute(final HttpExchange httpExchange) throws IOException{
        String requestMethod = httpExchange.getRequestMethod();
        String requestType = httpExchange.getRequestURI().getPath();
        URI requestURI = httpExchange.getRequestURI();
        Interpreter interpreter = new InterpreterImpl();

        if(ConstantHttp.HttpMethod.GET.equals(requestMethod) && requestType.matches(ConstantHttp.UrlPath.CHECK_PATH)) {
            String request = parseRequest(requestURI.getQuery());
            String receipt = null;
            try {
                receipt = interpreter.interpret(request.split(" "));
                writeResponse(httpExchange, receipt, ConstantHttp.HttpResponseStatus.STATUS_OK);
            } catch (NotEnoughProductsException | UnknownIdException | InvalidDataFormat e) {
                receipt = e.toString();
                writeResponse(httpExchange, receipt, ConstantHttp.HttpResponseStatus.STATUS_NOT_FOUND);
            }
        } else {
            notSupportRequest(httpExchange);
        }

    }

    private String parseRequest(String query) {
        return query.replaceAll("id=", "")
                .replaceAll("&value=", "-")
                .replaceAll("&", " ");
    }

    private void writeResponse(final HttpExchange httpExchange,String response, int status) throws IOException {
        Headers responseHeaders = httpExchange.getResponseHeaders();
        StringBuilder db = new StringBuilder();
        db.append(response);
        responseHeaders.add("Content-type", "text/*");
        httpExchange.sendResponseHeaders(status,  db.toString().getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream responseBody = httpExchange.getResponseBody()) {
            responseBody.write(db.toString().getBytes(StandardCharsets.UTF_8));
            responseBody.flush();
        }
    }

    private void notSupportRequest(final HttpExchange httpExchange) {
        try {
            httpExchange.sendResponseHeaders(ConstantHttp.HttpResponseStatus.STATUS_NOT_FOUND, 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
