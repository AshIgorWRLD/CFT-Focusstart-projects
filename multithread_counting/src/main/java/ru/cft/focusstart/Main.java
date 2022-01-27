package ru.cft.focusstart;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Slf4j
public class Main {
    public static void main(String[] args) {
        InputParser inputParser = new InputParser();
        long inputNumber = inputParser.parse();
        MyRow counter = new MyRow(inputNumber);
        double result;
        try {
            result = counter.countRow();
        } catch (ExecutionException e) {
            log.error("Can't count result");
            return;
        }
        log.info("Result of raw with N = {} is {}", inputNumber, result);
    }
}
