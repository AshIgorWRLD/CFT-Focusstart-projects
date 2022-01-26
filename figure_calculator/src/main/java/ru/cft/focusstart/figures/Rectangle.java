package ru.cft.focusstart.figures;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class Rectangle extends Figure {

    private float length;
    private float width;
    @Getter
    private float diagonal;

    public Rectangle(float firstSide, Float secondSide, String name, boolean isFileOutput) {
        super(name, isFileOutput);
        defineLengthAndWidth(firstSide, secondSide);
    }

    @Override
    protected void takeCharacteristics() {
        countArea();
        countPerimeter();
        countDiagonal();
    }

    @Override
    protected void printUniqueCharacteristic() {
        String outputString = "\nДлина диагонали: " + this.diagonal + " мм\n\n" + "Длина: "
                + this.length + " мм\n\n" + "Ширина: " + this.width + " мм";
        if (isFileOutput) {
            printStream.print(outputString);
        } else {
            log.info(outputString);
        }
    }

    @Override
    protected void countArea() {
        this.area = (float) (Math.ceil(this.length * this.width * SCALE) / SCALE);
    }

    @Override
    protected void countPerimeter() {
        this.perimeter = (float) (Math.ceil(2 * (this.length + this.width) * SCALE) / SCALE);
    }

    private void countDiagonal() {
        this.diagonal = (float) (Math.ceil((float) Math.sqrt(this.length *
                this.length + this.width * this.width) * SCALE) / SCALE);
    }

    private void defineLengthAndWidth(float firstNumber, float secondNumber) {
        if (firstNumber > secondNumber) {
            this.length = firstNumber;
            this.width = secondNumber;
        } else {
            this.length = secondNumber;
            this.width = firstNumber;
        }
    }
}