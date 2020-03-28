package com.my.notes.notes.exceptions;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.SENDER, locale = "fr")
public class NoteServiceException extends Exception {

    public NoteServiceException(String message) {
        super(message);
    }

    public NoteServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoteServiceException(Throwable cause) {
        super(cause);
    }
}
