package ru.cft.focusstart.client;

import org.apache.log4j.PropertyConfigurator;

public class ClientMain {
    public static void main(String[] args) {
        PropertyConfigurator.configure("src\\main\\resources\\" +
                "log4j.properties");
        ClientOpener clientOpener = new ClientOpener();
        clientOpener.open(args);
    }
}
