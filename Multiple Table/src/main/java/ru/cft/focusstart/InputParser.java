package ru.cft.focusstart;

import lombok.AllArgsConstructor;

import java.util.Scanner;

@AllArgsConstructor
public class InputParser {

    private static final String ONLY_NUMBERS_IN_INPUT = "\\d+";
    private final int lowestInputNumber;
    private final int highestInputNumber;

    public int takeTableSize() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter Multiplication Table size (" + lowestInputNumber + "-" +
                highestInputNumber + ")");
        while (true) {
            String inputString = scanner.nextLine();
            if (!isItNumber(inputString)) {
                System.out.println("Sorry, you need to input number between " + lowestInputNumber
                        + " and " + highestInputNumber);
                continue;
            }
            int inputNumber = Integer.parseInt(inputString);
            if (lowestInputNumber > inputNumber || highestInputNumber < inputNumber) {
                System.out.println("Sorry, you typed unavailable number, please use one" +
                        " of numbers between " + lowestInputNumber + " and " + highestInputNumber);
                continue;
            }
            scanner.close();
            return inputNumber;
        }
    }

    private boolean isItNumber(String string) {
        return string.matches(ONLY_NUMBERS_IN_INPUT);
    }
}
