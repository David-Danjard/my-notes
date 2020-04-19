package com.my.notes.ui.services.business;

import com.my.notes.ui.services.soap.SoapClient;
import generated.GetNotesByUserRequest;
import generated.NoteList;
import generated.ShareNoteRequest;
import generated.SimpleResponse;
import my_notes.notes.user.UserBean;
import my_notes.notes.user.UserByMailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ws.soap.client.core.SoapActionCallback;

@Service
public class NotesServices {

    private static final String SHARE_NOTE_SOAP_ACTION = "/shareNote";
    private final SoapClient notesSoapClient;
    private final SoapClient usersSoapClient;

    public NotesServices(@Qualifier("notesSoapClient") SoapClient soapClient, @Qualifier("usersSoapClient") SoapClient usersSoapClient) {
        this.notesSoapClient = soapClient;
        this.usersSoapClient = usersSoapClient;
    }

    public SimpleResponse shareNote(int userId, int noteId) {
        ShareNoteRequest shareNoteRequest = new ShareNoteRequest();
        shareNoteRequest.setNoteId(noteId);
        shareNoteRequest.setUserId(userId);

        return (SimpleResponse) notesSoapClient.callWebService(shareNoteRequest, new SoapActionCallback(SHARE_NOTE_SOAP_ACTION));
    }

    public NoteList getNotesByUser(String userEmail) {
        // Get the user information to get de the user id
        UserByMailRequest userByMailRequest = new UserByMailRequest();
        userByMailRequest.setEmail(userEmail);
        UserBean userBean = (UserBean) usersSoapClient.callWebService(userByMailRequest, new SoapActionCallback(""));

        // Create the get notes by user request
        GetNotesByUserRequest getNotesByUserRequest = new GetNotesByUserRequest();
        getNotesByUserRequest.setUserId(userBean.getId());
        return (NoteList) notesSoapClient.callWebService(getNotesByUserRequest, new SoapActionCallback("/getNotesByUser"));
    }

}
