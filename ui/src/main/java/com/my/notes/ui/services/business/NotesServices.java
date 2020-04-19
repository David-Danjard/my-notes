package com.my.notes.ui.services.business;

import com.my.notes.ui.services.soap.SoapClient;
import generated.ShareNoteRequest;
import generated.SimpleResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ws.soap.client.core.SoapActionCallback;

@Service
public class NotesServices {

    private static final String SHARE_NOTE_SOAP_ACTION = "/shareNote";
    private final SoapClient soapClient;

    public NotesServices(@Qualifier("notesSoapClient") SoapClient soapClient) {
        this.soapClient = soapClient;
    }

    public SimpleResponse shareNote(int userId, int noteId) {
        ShareNoteRequest shareNoteRequest = new ShareNoteRequest();
        shareNoteRequest.setNoteId(noteId);
        shareNoteRequest.setUserId(userId);

        return (SimpleResponse) soapClient.callWebService(shareNoteRequest, new SoapActionCallback(SHARE_NOTE_SOAP_ACTION));
    }

}
