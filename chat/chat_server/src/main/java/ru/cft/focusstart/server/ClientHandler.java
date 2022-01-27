package ru.cft.focusstart.server;

import ru.cft.focusstart.common.ProtocolCodes;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
public class ClientHandler implements Runnable {

    private final Socket socket;
    private final Server server;
    private final ProtocolCodes protocolCodes = new ProtocolCodes();
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    @Getter
    private String clientName;

    public ClientHandler(Socket socket, Server server) {
        this.server = server;
        this.socket = socket;
        try {
            dataInputStream = new DataInputStream(this.socket.getInputStream());
            dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            log.error("Unable to create data streams ", e);
        }
    }

    @SneakyThrows
    @Override
    public void run() {
        dataOutputStream.writeInt(protocolCodes.wrapCodeToInt(
                ProtocolCodes.CodeValue.READY_TO_TAKE_NAME));
        while (true) {
            log.info("SERVER TRY TO TAKE NEW CLIENT NAME");
            if (takeName()) {
                Date dateNow = new Date();
                SimpleDateFormat formatForDateNow = new SimpleDateFormat(
                        "dd.MM.yyyy',' hh:mm:ss a");
                server.sendMessageToAllClients(true, formatForDateNow.format(dateNow),
                        clientName, " connected to the chat");
                break;
            }
        }
        server.updateMembersAmountToAllClients();
        while (true) {
            log.info("WAIT THE MESSAGE");
            analyzeInput();
        }
    }

    public void analyzeInput() throws IOException {
        int inputCode = dataInputStream.readInt();
        log.info("CODE: " + inputCode);
        ProtocolCodes.CodeValue protocolCode = protocolCodes.unwrapCode(inputCode);
        if (ProtocolCodes.CodeValue.NEW_CHAT_MESSAGE.equals(protocolCode)) {
            String date = takeInputMessage();
            String inputMessage = takeInputMessage();
            server.sendMessageToAllClients(false, date, clientName, inputMessage);
        } else if (ProtocolCodes.CodeValue.END_OF_SESSION.equals(protocolCode)) {
            Date dateNow = new Date();
            SimpleDateFormat formatForDateNow = new SimpleDateFormat(
                    "dd.MM.yyyy',' hh:mm:ss a");
            server.sendMessageToAllClients(true, formatForDateNow.format(dateNow),
                    clientName, " left the chat");
            close();
        }
    }

    public void takeMessage(boolean isNotify, String date, String senderName, String message) {
        log.info("SEND MESSAGE GENERATED");
        try {
            dataOutputStream.writeInt(protocolCodes.wrapCodeToInt(
                    ProtocolCodes.CodeValue.NEW_CHAT_MESSAGE));
            dataOutputStream.writeBoolean(isNotify);
            dataOutputStream.writeUTF(date);
            dataOutputStream.writeUTF(senderName);
            dataOutputStream.writeUTF(message);
            log.info("MESSAGE RESENT");
        } catch (IOException e) {
            log.error("Unable to put your message in output stream ", e);
        }
    }

    private boolean takeName() throws IOException {
        log.info("READY TO READ NAME BYTES");
        String name = dataInputStream.readUTF();
        log.info("USER - " + name);
        if (server.isNameAlreadyTaken(name)) {
            log.info("NAME HAS TAKEN");
            dataOutputStream.writeInt(protocolCodes.wrapCodeToInt(
                    ProtocolCodes.CodeValue.NAME_ALREADY_TAKEN));
            return false;
        } else {
            log.info("NAME IS " + name);
            dataOutputStream.writeInt(protocolCodes.wrapCodeToInt(
                    ProtocolCodes.CodeValue.SUCCESSFULLY_ADDED_TO_THE_CHAT));
        }
        clientName = name;
        server.addNewClient(this);
        return true;
    }

    private String takeInputMessage() throws IOException {
        return dataInputStream.readUTF();
    }

    private void close() throws IOException {
        server.removeClient(this);
        server.updateMembersAmountToAllClients();
    }

    public void refreshChatMembersAmount(ArrayList<String> members) throws IOException {
        dataOutputStream.writeInt(protocolCodes.wrapCodeToInt(
                ProtocolCodes.CodeValue.CHAT_MEMBERS_AMOUNT_UPDATED));
        dataOutputStream.writeInt(members.size());
        members.forEach(member -> {
            try {
                dataOutputStream.writeUTF(member);
            } catch (IOException e) {
                log.error("can't refresh chat members", e);
            }
        });
    }
}
