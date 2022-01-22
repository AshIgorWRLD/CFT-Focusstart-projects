package ru.cft.focusstart;


public class MultiplicationTable {
    private static final StringBuilder stringBuilder = new StringBuilder();
    private final int lineElementsAmount;
    private final int spacesAmountForNumber;
    private final int firstSpacesNumber;
    private final StringBuilder splitStringBuilder = new StringBuilder();

    public MultiplicationTable(int size) {
        this.lineElementsAmount = size + 1;
        this.spacesAmountForNumber = getSpacesAmountForNumber(size * size);
        this.firstSpacesNumber = getSpacesAmountForNumber(size);
    }

    public void printTable() {
        stringBuilder.append("\n");
        String lineSplit = createLineSplit();
        for (int i = 0; i < this.lineElementsAmount; i++) {
            createNumbersLine(i);
            stringBuilder.append(lineSplit);
        }
        System.out.println(stringBuilder);
    }

    private void createNumbersLine(int initNumber) {
        if (initNumber != 0) {
            createFirstNumberInLine(initNumber);
        } else {
            createFirstEmptyBlock();
            initNumber = 1;
        }
        for (int i = 1; i < this.lineElementsAmount; ++i) {
            stringBuilder.append("|");
            createOneNumber(initNumber * i);
        }
        stringBuilder.append("\n");
    }

    private void createFirstNumberInLine(int number) {
        int numberLength = getSpacesAmountForNumber(number);
        for (int i = 0; i < this.firstSpacesNumber - numberLength; ++i) {
            stringBuilder.append(" ");
        }
        stringBuilder.append(number);
    }

    private void createOneNumber(int number) {
        int numberLength = getSpacesAmountForNumber(number);
        for (int i = 0; i < this.spacesAmountForNumber - numberLength; ++i) {
            stringBuilder.append(" ");
        }
        stringBuilder.append(number);
    }

    private void createFirstEmptyBlock() {
        for (int i = 0; i < this.firstSpacesNumber; ++i) {
            stringBuilder.append(" ");
        }
    }

    private String createLineSplit() {
        createFirstSplitBlock();
        for (int i = 1; i < this.lineElementsAmount; ++i) {
            splitStringBuilder.append("+");
            createOneSplitBlock();
        }
        splitStringBuilder.append("\n");
        return splitStringBuilder.toString();
    }

    private void createFirstSplitBlock() {
        for (int i = 0; i < this.firstSpacesNumber; ++i) {
            splitStringBuilder.append("-");
        }
    }

    private void createOneSplitBlock() {
        for (int i = 0; i < this.spacesAmountForNumber; ++i) {
            splitStringBuilder.append("-");
        }
    }

    private int getSpacesAmountForNumber(int number) {
        return Integer.toString(number).length();
    }
}
