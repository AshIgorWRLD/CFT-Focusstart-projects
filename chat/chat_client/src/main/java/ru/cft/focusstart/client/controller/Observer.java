package ru.cft.focusstart.client.controller;

public interface Observer {
    void takeChanges(ClientChanges clientChanges, ClientChangedInfo clientChangedInfo);
}
