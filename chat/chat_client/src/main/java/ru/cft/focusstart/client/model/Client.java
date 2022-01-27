package ru.cft.focusstart.client.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.cft.focusstart.client.controller.ClientChangedInfo;
import ru.cft.focusstart.client.controller.ClientChanges;
import ru.cft.focusstart.client.controller.Observable;
import ru.cft.focusstart.client.controller.Observer;
import ru.cft.focusstart.common.ProtocolCodes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class Client implements Observable {

    private final int port;
    private String serverIp;
    boolean connected = false;


    private final List<Observer> subscribers = new ArrayList<>();
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private ProtocolCodes protocolCodes = new ProtocolCodes();
    @Getter
    private String name;
    @Getter
    @Setter
    private ClientChangedInfo clientChangedInfo;
    @Getter
    @Setter
    private ClientChanges clientChanges;

    public Client(int port) {
        this.port = port;
    }

    public void setNameAndServerIp(String name, String serverIp) {
        this.name = name;
        this.serverIp = serverIp;
    }

    public void start() {
        Socket clientSocket;
        try {
            clientSocket = new Socket(InetAddress.getByName(serverIp), port);
        } catch (UnknownHostException e) {
            //observer.makeUnknownHostWindow();
            setClientChanges(ClientChanges.UNKNOWN_HOST);
            setClientChangedInfo(new ClientChangedInfo(null, null, false,
                    null, null, null));
            notifyObservers();
            return;
        } catch (IOException e) {
            log.error("CAN'T CREATE CLIENT SOCKET ", e);
            return;
        }

        try {
            log.info("CLIENT SOCKET: " + clientSocket);
            dataInputStream = new DataInputStream(clientSocket.getInputStream());
            dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            log.error("Unable to create data streams ", e);
            return;
        }

        log.info("CLIENT READY TO MESSAGE SERVER");

        Thread serverConnection = new Thread(new ClientTask(name, protocolCodes,
                dataInputStream, dataOutputStream, this));
        serverConnection.start();
        connected = true;
        log.info("STARTED");
    }

    public boolean isConnected() {
        return connected;
    }

    public void sendMessage(String message) throws IOException {
        dataOutputStream.writeInt(protocolCodes.wrapCodeToInt(
                ProtocolCodes.CodeValue.NEW_CHAT_MESSAGE));
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat(
                "dd.MM.yyyy',' hh:mm:ss a");
        dataOutputStream.writeUTF(formatForDateNow.format(dateNow));
        dataOutputStream.writeUTF(message);
    }

    public void notifyAboutLeaving() throws IOException {
        dataOutputStream.writeInt(protocolCodes.wrapCodeToInt(
                ProtocolCodes.CodeValue.END_OF_SESSION));
    }

    @Override
    public void addObserver(Observer observer) {
        subscribers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        subscribers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        log.info("OBSERVERS NOTIFIED " + subscribers.size());
        subscribers.forEach(subscriber -> {
            subscriber.takeChanges(clientChanges, clientChangedInfo);
        });
    }
}
