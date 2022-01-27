package ru.cft.focusstart.server;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class Server {
    private static final int BACKLOG = 1000;

    private final int port;
    private final String ip;
    private final ExecutorService threadPool = Executors.newCachedThreadPool();
    private final ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();
    private final ArrayList<String> clientNames = new ArrayList<>();
    private InetAddress address;
    private ServerSocket serverSocket;

    public Server(int port, String ip) {
        this.port = port;
        this.ip = ip;
    }

    public void start() {
        try {
            address = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            log.error("INCORRECT IP", e);
            System.exit(0);
        }
        try {
            serverSocket = new ServerSocket(port, BACKLOG, address);
        } catch (IOException e) {
            log.error("CAN'T OPEN SERVER SOCKET", e);
            System.exit(0);
        }
        log.info("SERVER IS STARTED");

        while (!serverSocket.isClosed()) {
            log.info("SERVER IS WAITING NEW CLIENT");
            Socket clientSocket;
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                log.error("CAN'T OPEN CLIENT SOCKET", e);
                continue;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(clientSocket.getInetAddress().toString()).
                    append("-").append(clientSocket.getPort());
            String newId = stringBuilder.toString();
            log.info("CLIENT " + newId + " CONNECTED TO CHAT");
            threadPool.submit(new ClientHandler(clientSocket, this));
        }
        threadPool.shutdown();
    }

    public void sendMessageToAllClients(boolean isNotify, String date, String senderName, String message) {
        log.info(clients.size() + " CLIENTS");
        for (ClientHandler client : clients) {
            client.takeMessage(isNotify, date, senderName, message);
        }
    }

    public void updateMembersAmountToAllClients() throws IOException {
        for (ClientHandler client : clients) {
            client.refreshChatMembersAmount(clientNames);
        }
    }

    public boolean isNameAlreadyTaken(String name) {
        AtomicBoolean isTakenFlag = new AtomicBoolean(false);
        log.info("THERE IS " + clients.size() + " MEMBERS");
        clients.forEach(client -> {
            log.info(client.getClientName());
            if (client.getClientName().equals(name)) {
                isTakenFlag.set(true);
            }
        });
        return isTakenFlag.get();
    }

    public void removeClient(ClientHandler client) {
        clientNames.remove(client.getClientName());
        clients.remove(client);
    }

    public void addNewClient(ClientHandler clientHandler) {
        clients.add(clientHandler);
        clientNames.add(clientHandler.getClientName());
    }

    @SneakyThrows
    public void closeServer() {
        serverSocket.close();
    }
}
