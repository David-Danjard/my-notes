package com.my.notes.ui.services.soap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.WebServiceException;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class SoapClient extends WebServiceGatewaySupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoapClient.class);

    public Object callWebService(Object request, WebServiceMessageCallback msgCallback) {
        WebServiceTemplate webServiceTemplate = getWebServiceTemplate();
        LOGGER.info("Call service url {}", webServiceTemplate.getDefaultUri());
        Object response;
        try {
            response = webServiceTemplate.marshalSendAndReceive(request, msgCallback);
        } catch (WebServiceException e) {
            LOGGER.error("Error on call webservice url " + webServiceTemplate.getDefaultUri(), e);
            throw e;
        }
        return response;
    }

}
