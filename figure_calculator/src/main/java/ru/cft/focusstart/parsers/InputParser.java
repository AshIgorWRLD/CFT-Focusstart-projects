package ru.cft.focusstart.parsers;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class InputParser {

    private static final int AVAILABLE_ARGUMENTS_NUMBER = 1;
    private static final String SPLIT_SIGN = " ";
    private static final String CIRCLE = "CIRCLE";
    private static final String RECTANGLE = "RECTANGLE";
    private static final String TRIANGLE = "TRIANGLE";

    public String takeInfo(File file) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String newLine = bufferedReader.readLine();
            if (newLine == null) {
                return null;
            }
            String[] subInput = newLine.split(SPLIT_SIGN);
            if (AVAILABLE_ARGUMENTS_NUMBER != subInput.length) {
                log.error("Sorry, too many arguments, you need only to enter figure type");
                return null;
            }
            if (!figureTypeCheck(newLine)) {
                log.error("Sorry, wrong figure type, you can only use" +
                        " CIRCLE, RECTANGLE and TRIANGLE");
                return null;
            }
            stringBuilder.append(newLine).append(" ");
            newLine = bufferedReader.readLine();
            if (newLine == null) {
                log.error("No parameters in file");
                return null;
            }
            stringBuilder.append(newLine);
            return stringBuilder.toString();
        } catch (IOException e) {
            log.error("Please check your input file, program can't find it");
            return null;
        }
    }

    private boolean figureTypeCheck(String figureName) {
        switch (figureName) {
            case CIRCLE, RECTANGLE, TRIANGLE -> {
                return true;
            }
        }
        return false;
    }
}