package ru.cft.focusstart.app;

import ru.cft.focusstart.controller.GameController;
import ru.cft.focusstart.model.gameboard.GameBoard;
import ru.cft.focusstart.view.windows.MainWindow;

public class Application {
    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard();
        MainWindow mainWindow = new MainWindow();
        GameController gameController = new GameController(gameBoard, mainWindow);
    }
}
