package com.my.notes.ui.services.business;

import com.my.notes.ui.services.soap.SoapClient;
import my_notes.notes.auth.IdentificationRequest;
import my_notes.notes.auth.UserBean;
import my_notes.notes.auth.UserPasswordToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ws.soap.client.core.SoapActionCallback;

@Service
public class AuthServices {

    private static final String AUTH_SOAP_ACTION = "http://my-notes/notes/auth/AuthPort/identificationRequest";
    private final SoapClient soapClient;

    @Value("${services.soap.auth.username}")
    private String userName;
    @Value("${services.soap.auth.pwd}")
    private String pwd;

    public AuthServices(@Qualifier("authSoapClient") SoapClient soapClient) {
        this.soapClient = soapClient;
    }

    public UserBean authenticateUser(UserPasswordToken userPasswordToken) {
        IdentificationRequest identificationRequest = new IdentificationRequest();
        identificationRequest.setUserPasswordToken(userPasswordToken);

        return (UserBean) soapClient.callWebService(identificationRequest, new SoapActionCallback(AUTH_SOAP_ACTION), userName, pwd);
    }

}
