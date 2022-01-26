package ru.cft.focusstart.figures;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Circle extends Figure {

    private static final float PI = (float) 3.1416;
    @Getter
    private final float radius;
    @Getter
    private float diameter;

    public Circle(float radius, String name, boolean isFileOutput) {
        super(name, isFileOutput);
        this.radius = radius;
    }

    @Override
    protected void takeCharacteristics() {
        countArea();
        countPerimeter();
        countDiameter();
    }

    @Override
    protected void printUniqueCharacteristic() {
        String outputString = "\nРадиус: " + this.radius + " мм\n\n" +
                "Диаметр: " + this.diameter + " мм";
        if (isFileOutput) {
            printStream.print(outputString);
        } else {
            log.info(outputString);
        }
    }

    @Override
    protected void countArea() {
        this.area = (float) (Math.ceil(PI * this.radius * this.radius * SCALE) / SCALE);
    }

    @Override
    protected void countPerimeter() {
        this.perimeter = (float) (Math.ceil(2 * PI * this.radius * SCALE) / SCALE);
    }

    private void countDiameter() {
        this.diameter = (float) (Math.ceil(2 * this.radius * SCALE) / SCALE);
    }
}