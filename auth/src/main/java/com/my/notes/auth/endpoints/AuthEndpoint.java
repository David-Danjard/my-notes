package com.my.notes.auth.endpoints;

import com.my.notes.auth.IdentificationRequest;
import com.my.notes.auth.UserBean;
import com.my.notes.auth.exceptions.UnauthorizedException;
import com.my.notes.auth.services.AuthServices;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class AuthEndpoint {

    private final AuthServices authServices;

    public AuthEndpoint(AuthServices authServices) {
        this.authServices = authServices;
    }

    @PayloadRoot(namespace = "http://my-notes/notes/auth", localPart = "identificationRequest")
    @ResponsePayload
    public UserBean authenticateUser(@RequestPayload IdentificationRequest identificationRequest) throws UnauthorizedException {
        return authServices.authenticateUser(
                identificationRequest.getUserPasswordToken()
        );
    }
}
