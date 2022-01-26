package ru.cft.focusstart.figures;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.FileOutputStream;
import java.io.PrintStream;

@Slf4j
public class Figure {

    protected static final double SCALE = Math.pow(10, 1);
    protected final boolean isFileOutput;
    @Getter
    protected float area;
    @Getter
    protected float perimeter;
    protected String name;
    protected FileOutputStream fileOutputStream;
    protected PrintStream printStream;

    @SneakyThrows
    public Figure(String name, boolean isFileOutput) {
        this.name = name;
        this.isFileOutput = isFileOutput;
        if (isFileOutput) {
            fileOutputStream = new FileOutputStream("src\\main\\resources\\ResultsFile.txt");
            printStream = new PrintStream(fileOutputStream);
        }
    }

    public void processFigure() {
        takeCharacteristics();
        printOptionalCharacteristics();
        printUniqueCharacteristic();
    }

    protected void takeCharacteristics() {
    }

    protected void countArea() {
    }

    protected void countPerimeter() {
    }

    protected void printUniqueCharacteristic() {
    }

    private void printOptionalCharacteristics() {
        String outputString = "\nТип фигуры: " + name + "\n\n" + "Площадь: " + this.area +
                " кв. мм\n\n" + "Периметр: " + this.perimeter + " мм\n\n";
        if (isFileOutput) {
            printStream.print(outputString);
        } else {
            log.info(outputString);
        }
    }
}