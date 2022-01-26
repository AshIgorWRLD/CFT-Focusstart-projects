package ru.cft.focusstart.figures;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Triangle extends Figure {

    private final float side1;
    private final float side2;
    private final float side3;
    @Getter
    private float corner1;
    @Getter
    private float corner2;
    @Getter
    private float corner3;

    public Triangle(float side1, float side2, float side3, String name, boolean isFileOutput) {
        super(name, isFileOutput);
        this.side1 = side1;
        this.side2 = side2;
        this.side3 = side3;
    }

    @Override
    protected void takeCharacteristics() {
        countArea();
        countPerimeter();
        countCorners();
    }

    @Override
    protected void printUniqueCharacteristic() {
        String outputString = ("\nПервая сторона и противолежащий ей угол: " + this.side1 + " мм - " +
                this.corner1 + "°\n\n" + "Вторая сторона и противолежащий ей угол: " +
                this.side2 + " мм - " + this.corner2 + "°\n\n" +
                "Третья сторона и противолежащий ей угол: " + this.side3 +
                " мм - " + this.corner3 + "°");
        if (isFileOutput) {
            printStream.print(outputString);
        } else {
            log.info(outputString);
        }
    }


    @Override
    protected void countArea() {
        float halfOfPerimeter = (side1 + side2 + side3) / 2;
        this.area = (float) Math.sqrt(halfOfPerimeter * (halfOfPerimeter - side1) *
                (halfOfPerimeter - side2) * (halfOfPerimeter - side3));
    }

    @Override
    protected void countPerimeter() {
        this.perimeter = side1 + side2 + side3;
    }

    private void countCorners() {
        float cos1 = (side2 * side2 + side3 * side3 - side1 * side1) / (2 * side2 * side3);
        float cos2 = (side1 * side1 + side3 * side3 - side2 * side2) / (2 * side1 * side3);
        float cos3 = (side1 * side1 + side2 * side2 - side3 * side3) / (2 * side1 * side2);
        corner1 = (float) (Math.ceil((float) Math.toDegrees(Math.acos(cos1)) * SCALE) / SCALE);
        corner2 = (float) (Math.ceil((float) Math.toDegrees(Math.acos(cos2)) * SCALE) / SCALE);
        corner3 = (float) (Math.ceil((float) Math.toDegrees(Math.acos(cos3)) * SCALE) / SCALE);
    }
}