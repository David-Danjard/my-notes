package com.my.notes.mails.utils;

import com.my.notes.stc.SimpleResponse;

public class SimpleResponseUtils {

    public static SimpleResponse getSuccessResponse(String message) {
        SimpleResponse simpleResponse = new SimpleResponse();
        simpleResponse.setMessage(message);
        simpleResponse.setStatus("OK");
        return simpleResponse;
    }

}
