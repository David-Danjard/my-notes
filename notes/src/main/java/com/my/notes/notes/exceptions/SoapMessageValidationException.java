package com.my.notes.notes.exceptions;

import org.springframework.ws.client.WebServiceClientException;

public class SoapMessageValidationException extends WebServiceClientException {
    public SoapMessageValidationException(String msg) {
        super(msg);
    }

    public SoapMessageValidationException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
