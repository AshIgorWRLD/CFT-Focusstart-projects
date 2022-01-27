package ru.cft.focusstart.server;

import org.apache.log4j.PropertyConfigurator;

public class ServerMain {
    public static void main(String[] args) {
        PropertyConfigurator.configure("src\\main\\resources\\" +
                "log4j.properties");
        ServerOpener serverOpener = new ServerOpener();
        serverOpener.open(args);
    }
}
