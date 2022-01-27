package ru.cft.focusstart.client.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;

@AllArgsConstructor
@Getter
public class ClientChangedInfo {
    private String userName;
    private ArrayList<String> names;
    private boolean isNotify;
    private String date;
    private String addressee;
    private String message;
}
