package ru.cft.focusstart;

public class ArgsParser {

    private static final int AVAILABLE_ARGUMENTS_NUMBER = 2;
    private static final String ONLY_NUMBERS_IN_INPUT = "\\d+";
    private static final String ERROR_MESSAGE = "Please check your start arguments, you need " +
            "to put there lowest and biggest available number in multiplication table";
    private final String[] arguments;
    private int firstNumber;
    private int secondNumber;

    public ArgsParser(String[] arguments) {
        this.arguments = arguments;
    }

    public boolean isInputArgsOk() {
        if (AVAILABLE_ARGUMENTS_NUMBER != arguments.length) {
            System.out.println(ERROR_MESSAGE);
            return false;
        }

        if (!(arguments[0].matches(ONLY_NUMBERS_IN_INPUT)) ||
                !(arguments[1].matches(ONLY_NUMBERS_IN_INPUT))) {
            System.out.println(ERROR_MESSAGE);
            return false;
        }

        firstNumber = Integer.parseInt(arguments[0]);
        secondNumber = Integer.parseInt(arguments[1]);

        if (firstNumber == secondNumber) {
            System.out.println("Sorry, you need to put different numbers in start arguments");
        }
        return true;
    }

    public TableBorders sortArgs() {
        if (firstNumber > secondNumber) {
            return new TableBorders(secondNumber, firstNumber);
        }
        return new TableBorders(firstNumber, secondNumber);
    }

}
