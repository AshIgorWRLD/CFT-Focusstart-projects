package ru.cft.focusstart;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.cft.focusstart.figures.Circle;
import ru.cft.focusstart.figures.Figure;
import ru.cft.focusstart.figures.Rectangle;
import ru.cft.focusstart.figures.Triangle;

import java.lang.reflect.InvocationTargetException;

@Slf4j
@AllArgsConstructor
public class FigureFabric {

    private static final String CIRCLE = "CIRCLE";
    private static final String RECTANGLE = "RECTANGLE";
    private static final String TRIANGLE = "TRIANGLE";
    private static final String SPLIT_SIGN = " ";
    private static final String ONLY_NUMBERS_IN_INPUT = "\\d+";
    private static final int AVAILABLE_ARGUMENTS_NUMBER_FOR_CIRCLE = 2;
    private static final int AVAILABLE_ARGUMENTS_NUMBER_FOR_RECTANGLE = 3;
    private static final int AVAILABLE_ARGUMENTS_NUMBER_FOR_TRIANGLE = 4;

    private final boolean isFileOutput;

    public Figure createFigureByName(String fileData)
            throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        String[] arguments = fileData.split(SPLIT_SIGN);
        String russianName = getRussianName(arguments[0]);

        return isInputParametersOk(arguments, russianName);
    }

    private Figure isInputParametersOk(String[] arguments, String russianName) {
        switch (arguments[0]) {
            case CIRCLE -> {
                if (AVAILABLE_ARGUMENTS_NUMBER_FOR_CIRCLE != arguments.length) {
                    log.error("Sorry, wrong arguments number, remember, circle - radius, " +
                            "rectangle - 2 sides, triangle - 3 sides");
                    return null;
                }
                if (!(arguments[1].matches(ONLY_NUMBERS_IN_INPUT))) {
                    log.error("Sorry, you need to input number");
                    return null;
                }
                return new Circle(Float.parseFloat(arguments[1]), russianName, isFileOutput);
            }
            case RECTANGLE -> {
                if (AVAILABLE_ARGUMENTS_NUMBER_FOR_RECTANGLE != arguments.length) {
                    log.error("Sorry, wrong arguments number, remember, circle - radius, " +
                            "rectangle - 2 sides, triangle - 3 sides");
                    return null;
                }

                if (!(arguments[1].matches(ONLY_NUMBERS_IN_INPUT) &&
                        arguments[2].matches(ONLY_NUMBERS_IN_INPUT))) {
                    log.error("Sorry, you need to input numbers");
                    return null;
                }
                return new Rectangle(Float.parseFloat(arguments[1]), Float.parseFloat(arguments[2]),
                        russianName, isFileOutput);
            }
            case TRIANGLE -> {
                if (AVAILABLE_ARGUMENTS_NUMBER_FOR_TRIANGLE != arguments.length) {
                    log.error("Sorry, wrong arguments number, remember, circle - radius, " +
                            "rectangle - 2 sides, triangle - 3 sides");
                    return null;
                }

                if (!(arguments[1].matches(ONLY_NUMBERS_IN_INPUT) &&
                        arguments[2].matches(ONLY_NUMBERS_IN_INPUT) &&
                        arguments[3].matches(ONLY_NUMBERS_IN_INPUT))) {
                    log.error("Sorry, you need to input numbers");
                    return null;
                }

                TriangleSides triangleSides = defineAllTriangleSides(arguments);
                if (triangleSides == null) {
                    log.error("Sorry, your parameters can't make triangle");
                    return null;
                }

                return new Triangle(triangleSides.getSide1(), triangleSides.getSide2(),
                        triangleSides.getSide3(), russianName, isFileOutput);
            }
        }
        return null;
    }

    private String getRussianName(String name) {
        switch (name) {
            case "CIRCLE" -> {
                return "круг";
            }
            case "RECTANGLE" -> {
                return "прямоугольник";
            }
            case "TRIANGLE" -> {
                return "треугольник";
            }
        }
        return null;
    }

    private TriangleSides defineAllTriangleSides(String[] arguments) {
        TriangleSides triangleSides = new TriangleSides(Float.parseFloat(arguments[1]),
                Float.parseFloat(arguments[2]), Float.parseFloat(arguments[3]));
        if (!(triangleSides.getSide1() + triangleSides.getSide2() <= triangleSides.getSide3())
                && !(triangleSides.getSide1() + triangleSides.getSide3() <= triangleSides.getSide2())
                && !(triangleSides.getSide2() + triangleSides.getSide3() <= triangleSides.getSide1())) {
            return triangleSides;
        }
        return null;
    }

    public Figure takeFigure(String fileData) {
        Figure figure;
        try {
            figure = createFigureByName(fileData);
            if (figure == null) {
                return null;
            }
            figure.processFigure();
            return figure;
        } catch (NoSuchMethodException | ClassNotFoundException | InvocationTargetException |
                InstantiationException | IllegalAccessException e) {
            return null;
        }
    }
}