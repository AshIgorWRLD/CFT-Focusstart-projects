package ru.cft.focusstart.common;

public class ProtocolCodes {

    public enum CodeValue {
        NAME_ALREADY_TAKEN,
        SUCCESSFULLY_ADDED_TO_THE_CHAT,
        CHAT_MEMBERS_AMOUNT_UPDATED,
        NEW_CHAT_MESSAGE,
        READY_TO_TAKE_NAME,
        END_OF_SESSION
    }

    public int wrapCodeToInt(CodeValue codeValue) {
        switch (codeValue) {
            case NAME_ALREADY_TAKEN -> {
                return 0;
            }
            case SUCCESSFULLY_ADDED_TO_THE_CHAT -> {
                return 1;
            }
            case CHAT_MEMBERS_AMOUNT_UPDATED -> {
                return 2;
            }
            case NEW_CHAT_MESSAGE -> {
                return 3;
            }
            case READY_TO_TAKE_NAME -> {
                return 4;
            }
            case END_OF_SESSION -> {
                return 5;
            }
        }
        return -1;
    }

    public CodeValue unwrapCode(int code) {
        switch (code) {
            case 0 -> {
                return CodeValue.NAME_ALREADY_TAKEN;
            }
            case 1 -> {
                return CodeValue.SUCCESSFULLY_ADDED_TO_THE_CHAT;
            }
            case 2 -> {
                return CodeValue.CHAT_MEMBERS_AMOUNT_UPDATED;
            }
            case 3 -> {
                return CodeValue.NEW_CHAT_MESSAGE;
            }
            case 4 -> {
                return CodeValue.READY_TO_TAKE_NAME;
            }
            case 5 -> {
                return CodeValue.END_OF_SESSION;
            }
        }
        return null;
    }
}
