package com.my.notes.notes.services.validation;

import com.my.notes.notes.exceptions.SoapMessageValidationException;

public interface SoapMessageValidator {

    void validateMessage(Object message) throws SoapMessageValidationException;

}
