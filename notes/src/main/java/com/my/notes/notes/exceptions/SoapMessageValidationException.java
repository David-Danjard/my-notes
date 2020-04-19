package com.my.notes.notes.exceptions;

import org.springframework.ws.WebServiceException;

public class SoapMessageValidationException extends WebServiceException {
    public SoapMessageValidationException(String msg) {
        super(msg);
    }

    public SoapMessageValidationException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
