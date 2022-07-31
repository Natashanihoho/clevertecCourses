package ru.clevertec.gordievich.infrastructure.exceptions;

import lombok.Builder;

@Builder
public class ExceptionInfo {

    private String message;
    private String[] stackTrace;

}
