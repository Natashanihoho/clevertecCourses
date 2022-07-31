package ru.clevertec.gordievich.infrastructure.validation;

public class InvalidDataFormat extends Exception{

    public InvalidDataFormat(String message) {
        super(message);
    }
}
