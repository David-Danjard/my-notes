package com.my.notes.mails.endpoints;

import com.my.notes.mails.Email;
import com.my.notes.mails.services.MailServices;
import com.my.notes.mails.utils.SimpleResponseUtils;
import com.my.notes.stc.SimpleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.addressing.server.annotation.Action;

import java.util.Base64;

@Endpoint
public class MailsEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailsEndpoint.class);

    private final MailServices mailServices;

    @Autowired
    public MailsEndpoint(MailServices mailServices) {
        this.mailServices = mailServices;
    }

    @Action("/html-send")
    @ResponsePayload
    public SimpleResponse sendHTMLMessage(@RequestPayload Email email) {
        LOGGER.info("Envoi en HTML : {}", email.getContent());
        LOGGER.info("Contenu decode : {}", new String(Base64.getDecoder().decode(email.getContent())));
        mailServices.sendEmail(email, true);
        return SimpleResponseUtils.getSuccessResponse("Email HTML envoyé !!!");
    }

    @Action("/text-send")
    @ResponsePayload
    public SimpleResponse sendTextMessage(@RequestPayload Email email) {
        LOGGER.info("Envoi en Texte : {}", email.getContent());
        mailServices.sendEmail(email, false);
        return SimpleResponseUtils.getSuccessResponse("Email TEXTE envoyé !!!");
    }

}
