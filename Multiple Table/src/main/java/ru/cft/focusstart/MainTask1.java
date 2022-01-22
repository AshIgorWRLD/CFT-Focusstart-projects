package ru.cft.focusstart;

public class MainTask1 {

    public static void main(String[] args) {
        ArgsParser argsParser = new ArgsParser(args);
        if (!argsParser.isInputArgsOk()) {
            return;
        }
        TableBorders tableBorders = argsParser.sortArgs();
        InputParser inputParser = new InputParser(tableBorders.getLowestNumber(),
                tableBorders.getHighestNumber());
        int tableSize = inputParser.takeTableSize();
        MultiplicationTable multiplicationTable = new MultiplicationTable(tableSize);
        multiplicationTable.printTable();
    }
}
