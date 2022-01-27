package ru.cft.focusstart;

import lombok.extern.slf4j.Slf4j;
import ru.cft.focusstart.parsers.InputParser;

import java.io.File;

@Slf4j
public class Main {

    public static void main(String[] args) {

        if (args.length != 2) {
            log.error("wrong arguments number");
            return;
        }
        File inputFile = new File(args[0]);
        InputParser inputParser = new InputParser();
        String figureInfo = inputParser.takeInfo(inputFile);
        if (null == figureInfo) {
            return;
        }
        boolean isFileOutput;
        if ("file".equals(args[1])) {
            isFileOutput = true;
        } else if ("cons".equals(args[1])) {
            isFileOutput = false;
        } else {
            return;
        }
        FigureFabric figureFabric = new FigureFabric(isFileOutput);
        figureFabric.takeFigure(figureInfo);
    }
}