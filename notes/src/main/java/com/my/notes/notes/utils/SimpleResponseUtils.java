package com.my.notes.notes.utils;

import com.my.notes.notes.SimpleResponse;

public class SimpleResponseUtils {

    public static SimpleResponse getSuccessResponse(String message) {
        SimpleResponse simpleResponse = new SimpleResponse();
        simpleResponse.setMessage(message);
        simpleResponse.setStatus("OK");
        return simpleResponse;
    }

}
