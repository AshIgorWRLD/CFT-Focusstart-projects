package ru.cft.focusstart.client.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.cft.focusstart.client.model.Client;
import ru.cft.focusstart.client.view.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

@Slf4j
public class WindowController {
    private final Client client;
    private final MainWindow mainWindow;

    public WindowController(Client client, MainWindow mainWindow) {
        this.client = client;
        this.mainWindow = mainWindow;

        this.mainWindow.setLogInInfoButtonListener(new TakeLogInInfoListener());
        this.mainWindow.setSendMessageButtonListener(new SendMessageListener());
        this.mainWindow.addWindowListener(new CloseWindowListener());
        mainWindow.setVisible(true);
    }

    private class TakeLogInInfoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = mainWindow.takeNameText();
            String serverIp = mainWindow.takeServerIpText();
            if (name == null) {
                JOptionPane.showMessageDialog(mainWindow,
                        "Please, enter your name");
                return;
            }
            if (serverIp == null) {
                JOptionPane.showMessageDialog(mainWindow,
                        "Please, enter server IP");
                return;
            }

            client.setNameAndServerIp(name, serverIp);
            client.start();
        }
    }

    private class SendMessageListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String text = mainWindow.takeMessageText();
            try {
                client.sendMessage(text);
            } catch (IOException ex) {
                log.error("Unable to send message ", ex);
            }
        }
    }

    private class CloseWindowListener implements WindowListener {

        @Override
        public void windowOpened(WindowEvent e) {
        }

        @SneakyThrows
        @Override
        public void windowClosing(WindowEvent e) {
            if (client.isConnected()) {
                client.notifyAboutLeaving();
            }
        }

        @Override
        public void windowClosed(WindowEvent e) {
        }

        @Override
        public void windowIconified(WindowEvent e) {
        }

        @Override
        public void windowDeiconified(WindowEvent e) {
        }

        @Override
        public void windowActivated(WindowEvent e) {
        }

        @Override
        public void windowDeactivated(WindowEvent e) {
        }
    }
}
