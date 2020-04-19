package com.my.notes.ui.services.business;

import com.my.notes.ui.services.soap.SoapClient;
import generated.*;
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
    private final UsersServices usersServices;

    public NotesServices(@Qualifier("notesSoapClient") SoapClient soapClient, UsersServices usersServices) {
        this.notesSoapClient = soapClient;
        this.usersServices = usersServices;
    }

    public SimpleResponse shareNote(int userId, int noteId) {
        ShareNoteRequest shareNoteRequest = new ShareNoteRequest();
        shareNoteRequest.setNoteId(noteId);
        shareNoteRequest.setUserId(userId);

        return (SimpleResponse) notesSoapClient.callWebService(shareNoteRequest, new SoapActionCallback(SHARE_NOTE_SOAP_ACTION));
    }

    public NoteList getNotesByUser(String userEmail) {
        // Get the user information to get de the user id
        UserBean userBean = usersServices.getUserByEmail(userEmail);

        // Create the get notes by user request
        GetNotesByUserRequest getNotesByUserRequest = new GetNotesByUserRequest();
        getNotesByUserRequest.setUserId(userBean.getId());
        return (NoteList) notesSoapClient.callWebService(getNotesByUserRequest, new SoapActionCallback("/getNotesByUser"));
    }

    public void addNote(Note note) {
        notesSoapClient.callWebService(note, new SoapActionCallback("/addNote"));
    }

}
