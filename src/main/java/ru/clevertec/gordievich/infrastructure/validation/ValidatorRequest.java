package ru.clevertec.gordievich.infrastructure.validation;

import ru.clevertec.gordievich.infrastructure.connection.PropertiesUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ValidatorRequest {

    private final String ID_REGEX = "^[1-9]\\d?$|^100$";
    private final String NAME_REGEX = "^[A-Z][a-z]{2,29}$|^[А-я][а-я]{2,29}$";
   //private final String NAME_REGEX = "^[A-Z][a-z]{2,29}$";
    private final String PRICE_REGEX = "^[1-9]\\d?\\.\\d\\d$|^100\\.00$";
    private final String COUNT_REGEX = "^[1-9]$|^1\\d$|^20$";

    private final String DELIMETER = ";";

    private final String sourceFilePath;

    private StringBuilder invalidDataBuilder = new StringBuilder();
    private List<String> correctPositionsList = new ArrayList<>();

    public ValidatorRequest(String sourceFilePath) {
        this.sourceFilePath = sourceFilePath;
    }

    public List<String> getCorrectPositions() throws IOException {

        try(Stream<String> lines = Files.lines(Path.of(sourceFilePath), StandardCharsets.UTF_8)) {
            lines.forEach(line -> {
                if(isCorrectPosition(line)) {
                    correctPositionsList.add(line);
                } else {
                    invalidDataBuilder.append(line + "\n");
                }
            });
        }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(PropertiesUtil.get("INVALID_DATA_FILE_PATH"), StandardCharsets.UTF_8))) {
           writer.write(invalidDataBuilder.toString());
        }

        return correctPositionsList;
    }

    private boolean isCorrectPosition(String position) {
        String[] split = position.split(DELIMETER);
        String id = split[0];
        String name = split[1];
        String price = split[2];
        String count = split[3];

        return id.matches(ID_REGEX)
                && name.matches(NAME_REGEX)
                && price.matches(PRICE_REGEX)
                && count.matches(COUNT_REGEX);
    }

}

