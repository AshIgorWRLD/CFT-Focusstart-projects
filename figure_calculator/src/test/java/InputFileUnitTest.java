import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.Test;
import ru.cft.focusstart.FigureFabric;
import ru.cft.focusstart.figures.Circle;
import ru.cft.focusstart.figures.Figure;
import ru.cft.focusstart.figures.Rectangle;
import ru.cft.focusstart.figures.Triangle;
import ru.cft.focusstart.parsers.InputParser;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class InputFileUnitTest {

    @Test
    public void wrongFigureTypeTest() {
        BasicConfigurator.configure();
        File inputFile = new File("src\\test\\resources\\InputFiles\\WrongFigure.txt");
        InputParser inputParser = new InputParser();
        String fileInfo = inputParser.takeInfo(inputFile);
        assertNull(fileInfo);
        log.info("TEST: wrongFigureTypeTest - COMPLETED");
    }

    @Test
    public void wrongParametersAmountTest() {
        BasicConfigurator.configure();
        File inputFile = new File("src\\test\\resources\\InputFiles\\WrongParametersAmount.txt");
        InputParser inputParser = new InputParser();
        String fileData = inputParser.takeInfo(inputFile);
        FigureFabric figureFabric = new FigureFabric(true);
        Figure figure = figureFabric.takeFigure(fileData);
        assertNull(figure);
        log.info("TEST: wrongParametersAmountTest - COMPLETED");
    }

    @Test
    public void circleParametersTest() {
        BasicConfigurator.configure();


        File inputFile = new File("src\\test\\resources\\InputFiles\\RightCircle.txt");
        float expectedArea = (float) 78.6;
        float expectedPerimeter = (float) 31.5;
        float expectedDiameter = 10;

        InputParser inputParser = new InputParser();
        String fileInfo = inputParser.takeInfo(inputFile);
        FigureFabric figureFabric = new FigureFabric(true);
        Circle figure = (Circle) figureFabric.takeFigure(fileInfo);
        assertEquals(expectedArea, figure.getArea());
        assertEquals(expectedPerimeter, figure.getPerimeter());
        assertEquals(expectedDiameter, figure.getDiameter());
        log.info("TEST: circleParametersTest - COMPLETED");
    }

    @Test
    public void rectangleParametersTest() {
        BasicConfigurator.configure();

        File inputFile = new File("src\\test\\resources\\InputFiles\\RightRectangle.txt");
        float expectedArea = 90;
        float expectedPerimeter = 38;
        float expectedDiagonal = (float) 13.5;

        InputParser inputParser = new InputParser();
        String fileInfo = inputParser.takeInfo(inputFile);
        FigureFabric figureFabric = new FigureFabric(true);
        Rectangle figure = (Rectangle) figureFabric.takeFigure(fileInfo);
        assertEquals(expectedArea, figure.getArea());
        assertEquals(expectedPerimeter, figure.getPerimeter());
        assertEquals(expectedDiagonal, figure.getDiagonal());
        log.info("TEST: rectangleParametersTest - COMPLETED");
    }

    @Test
    public void triangleParametersTest() {
        BasicConfigurator.configure();

        File inputFile = new File("src\\test\\resources\\InputFiles\\RightTriangle.txt");
        float expectedArea = 6;
        float expectedPerimeter = 12;
        float expectedCorner1 = (float) 36.9;
        float expectedCorner2 = (float) 53.2;
        float expectedCorner3 = 90;

        InputParser inputParser = new InputParser();
        String fileInfo = inputParser.takeInfo(inputFile);
        FigureFabric figureFabric = new FigureFabric(true);
        Triangle figure = (Triangle) figureFabric.takeFigure(fileInfo);
        assertEquals(expectedArea, figure.getArea());
        assertEquals(expectedPerimeter, figure.getPerimeter());
        assertEquals(expectedCorner1, figure.getCorner1());
        assertEquals(expectedCorner2, figure.getCorner2());
        assertEquals(expectedCorner3, figure.getCorner3());
        log.info("TEST: rectangleParametersTest - COMPLETED");
    }

    @Test
    public void wrongTriangleSidesRatio() {
        BasicConfigurator.configure();

        File inputFile = new File("src\\test\\resources\\InputFiles\\WrongTriangleSidesRatio.txt");

        InputParser inputParser = new InputParser();
        String fileInfo = inputParser.takeInfo(inputFile);
        FigureFabric figureFabric = new FigureFabric(true);
        Triangle figure = (Triangle) figureFabric.takeFigure(fileInfo);
        assertNull(figure);
        log.info("TEST: wrongTriangleSidesRatio - COMPLETED");
    }

    @Test
    public void wrongParametersTypeTest() {
        BasicConfigurator.configure();

        File inputFile = new File("src\\test\\resources\\InputFiles\\WrongParametersType.txt");

        InputParser inputParser = new InputParser();
        String fileInfo = inputParser.takeInfo(inputFile);
        FigureFabric figureFabric = new FigureFabric(true);
        Triangle figure = (Triangle) figureFabric.takeFigure(fileInfo);
        assertNull(figure);
        log.info("TEST: wrongParametersTypeTest - COMPLETED");
    }
}