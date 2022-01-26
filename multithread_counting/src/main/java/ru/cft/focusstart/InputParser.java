package ru.cft.focusstart;

import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class InputParser {

    private static final String ONLY_NUMBERS_IN_INPUT = "\\d+";
    private static final String SPLIT_SIGN = " ";
    private static final int AVAILABLE_LENGTH = 1;
    private static final long HIGH_BORDER = 2000000000;
    private static final int LOW_BORDER = 16;

    public long parse() {
        log.info("Please enter your number");

        String inputString;
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                inputString = scanner.nextLine();
                if (argumentsNumberCheck(inputString) && argumentsTypeCheck(inputString)) {
                    long inputNumber = Long.parseLong(inputString);
                    if (bordersCheck(inputNumber)) {
                        return inputNumber;
                    } else {
                        log.info("Please enter integer number bigger than 15 and smaller than 2000000000");
                    }
                } else {
                    log.info("Please enter one number");
                }
            }
        }
    }

    private boolean argumentsNumberCheck(String inputString) {
        String[] subString = inputString.split(SPLIT_SIGN);
        return AVAILABLE_LENGTH == subString.length;
    }

    private boolean argumentsTypeCheck(String inputString) {
        return inputString.matches(ONLY_NUMBERS_IN_INPUT);
    }

    private boolean bordersCheck(long inputNumber) {
        return (LOW_BORDER <= inputNumber && HIGH_BORDER >= inputNumber);
    }
}
