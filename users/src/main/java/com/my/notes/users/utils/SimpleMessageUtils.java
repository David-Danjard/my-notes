package com.my.notes.users.utils;

import com.my.notes.users.SimpleMessage;

public class SimpleMessageUtils {

    public static SimpleMessage getSuccessResponse(String message) {
        SimpleMessage simpleResponse = new SimpleMessage();
        simpleResponse.setMessage(message);
        simpleResponse.setStatus("OK");
        return simpleResponse;
    }

}
