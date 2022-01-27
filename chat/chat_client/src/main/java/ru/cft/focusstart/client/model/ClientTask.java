package ru.cft.focusstart.client.model;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.cft.focusstart.client.controller.ClientChangedInfo;
import ru.cft.focusstart.client.controller.ClientChanges;
import ru.cft.focusstart.common.ProtocolCodes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

@Slf4j
public class ClientTask implements Runnable {

    private final String name;
    private final ProtocolCodes protocolCodes;
    private final Client client;

    private final DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public ClientTask(String name, ProtocolCodes protocolCodes,
                      DataInputStream dataInputStream, DataOutputStream dataOutputStream, Client client) {
        this.name = name;
        this.protocolCodes = protocolCodes;
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
        this.client = client;
    }

    @SneakyThrows
    @Override
    public void run() {

        while (true) {
            try {
                parseMessage();
            } catch (IOException e) {
                log.error("Error in parsing message from server ", e);
            }
        }
    }

    public void parseMessage() throws IOException {
        int inputCode = dataInputStream.readInt();
        log.info("CODE: " + inputCode);
        ProtocolCodes.CodeValue protocolCode = protocolCodes.unwrapCode(inputCode);
        if (ProtocolCodes.CodeValue.NAME_ALREADY_TAKEN.equals(protocolCode)) {
            //observer.makeUnavailableNameWindow();
            client.setClientChanges(ClientChanges.UNAVAILABLE_NAME);
            client.setClientChangedInfo(new ClientChangedInfo(null, null, false,
                    null, null, null));
            client.notifyObservers();
        } else if (ProtocolCodes.CodeValue.SUCCESSFULLY_ADDED_TO_THE_CHAT.equals(protocolCode)) {
            //observer.userConnected(name);
            log.info("SUCCESSFULLY ADDED TO THE CHAT");
            client.setClientChanges(ClientChanges.USER_CONNECTED);
            client.setClientChangedInfo(new ClientChangedInfo(name, null, false,
                    null, null, null));
            client.notifyObservers();
        } else if (ProtocolCodes.CodeValue.CHAT_MEMBERS_AMOUNT_UPDATED.equals(protocolCode)) {
            takeNewChatMembersAmount();
        } else if (ProtocolCodes.CodeValue.NEW_CHAT_MESSAGE.equals(protocolCode)) {
            takeNewMessage();
        } else if (ProtocolCodes.CodeValue.READY_TO_TAKE_NAME.equals(protocolCode)) {
            sendName();
        }
    }

    @SneakyThrows
    private void sendName() {
        try {
            dataOutputStream.writeUTF(name);
            log.info("NAME SENT TO SERVER");
        } catch (IOException e) {
            log.error("can't send message to server", e);
        }
    }

    private void takeNewChatMembersAmount() throws IOException {
        int amount = dataInputStream.readInt();
        log.info("AMOUNT: " + amount);
        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            names.add(dataInputStream.readUTF());
        }
        //observer.updateParticipantsAmount(names);
        client.setClientChanges(ClientChanges.MEMBERS_AMOUNT_UPDATED);
        client.setClientChangedInfo(new ClientChangedInfo(null, names, false,
                null, null, null));
        client.notifyObservers();
    }

    private void takeNewMessage() throws IOException {
        boolean isNotify = dataInputStream.readBoolean();
        String date = dataInputStream.readUTF();
        String addresseeName = dataInputStream.readUTF();
        String inString = dataInputStream.readUTF();
        //observer.addNewMessage(isNotify, date, addresseeName, inString);
        client.setClientChanges(ClientChanges.NEW_MESSAGE);
        client.setClientChangedInfo(new ClientChangedInfo(null, null, isNotify,
                date, addresseeName, inString));
        client.notifyObservers();
    }

}
