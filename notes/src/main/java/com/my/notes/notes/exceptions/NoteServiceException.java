package com.my.notes.notes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.CLIENT, locale = "fr")
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
