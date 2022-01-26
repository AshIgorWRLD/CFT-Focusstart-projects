import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import ru.cft.focusstart.parsers.InputParser;

import java.io.File;

@Slf4j
public class InputUnitTest {

    @Test
    void wrongFilePathTest() {
        BasicConfigurator.configure();

        File inputFile = new File("ssssss");

        InputParser inputParser = new InputParser();
        String inputCheckResult = inputParser.takeInfo(inputFile);
        assertNull(inputCheckResult);
        log.info("TEST: wrongFilePathTest - COMPLETED");
    }

    @Test
    void rightFilePathTest() {
        BasicConfigurator.configure();

        File inputFile = new File("src\\test\\resources\\InputFiles\\RightCircle.txt");

        InputParser inputParser = new InputParser();
        String inputCheckResult = inputParser.takeInfo(inputFile);
        assertEquals("CIRCLE 5", inputCheckResult);
        log.info("TEST: rightFilePathTest - COMPLETED");
    }

    @Test
    void emptyFileTest() {
        BasicConfigurator.configure();

        File inputFile = new File("src\\test\\resources\\InputFiles\\EmptyFile.txt");

        InputParser inputParser = new InputParser();
        String inputCheckResult = inputParser.takeInfo(inputFile);
        assertNull(inputCheckResult);
        log.info("TEST: emptyFileTest - COMPLETED");
    }

    @Test
    public void NoParametersTest() {
        BasicConfigurator.configure();

        File inputFile = new File("src\\test\\resources\\InputFiles\\NoParametersTriangle.txt");

        InputParser inputParser = new InputParser();
        String inputCheckResult = inputParser.takeInfo(inputFile);
        assertNull(inputCheckResult);
        log.info("TEST: NoParametersTriangle - COMPLETED");
    }
}