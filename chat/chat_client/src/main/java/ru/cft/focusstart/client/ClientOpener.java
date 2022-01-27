package ru.cft.focusstart.client;

import lombok.extern.slf4j.Slf4j;
import ru.cft.focusstart.client.controller.WindowController;
import ru.cft.focusstart.client.model.Client;
import ru.cft.focusstart.client.view.MainWindow;

@Slf4j
public class ClientOpener {
    private static final int ARGUMENTS_NUMBER = 1;
    private static final int MINIMAL_AVAILABLE_PORT_NUMBER = 1024;
    private static final int MAXIMAL_AVAILABLE_PORT_NUMBER = 49151;
    private static final String NUMBERS_IN_STRING = ".*\\d+.*";

    public void open(String[] args) {
        if (args.length != ARGUMENTS_NUMBER) {
            log.info("WRONG ARGUMENTS NUMBER (you need 1)");
            return;
        }
        if (NUMBERS_IN_STRING.equals(args[0])) {
            log.info("ARGUMENT SHOULD BE PORT NUMBER " + args[0]);
            return;
        }
        int port = Integer.parseInt(args[0]);
        if (MINIMAL_AVAILABLE_PORT_NUMBER > port || MAXIMAL_AVAILABLE_PORT_NUMBER < port) {
            log.info("PLEASE USE PORTS IN RANGE " + MINIMAL_AVAILABLE_PORT_NUMBER + "-"
                    + MAXIMAL_AVAILABLE_PORT_NUMBER);
            return;
        }
        MainWindow mainWindow = new MainWindow();
        Client client = new Client(port);
        client.addObserver(mainWindow);
        WindowController windowController = new WindowController(client, mainWindow);
    }
}
