package com.my.notes.notes.endpoints;

import com.my.notes.notes.GetNotesByUserRequest;
import com.my.notes.notes.NoteList;
import com.my.notes.notes.exceptions.NoteServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.server.endpoint.annotation.SoapAction;

import java.time.LocalDateTime;

@Endpoint
public class NotesEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotesEndpoint.class);

    @SoapAction("/getNotesByUser")
    @ResponsePayload
    public NoteList getNotesForUser(@RequestPayload GetNotesByUserRequest getNotesByUserRequest) throws Exception {
        LOGGER.info("Appel reçu sur le endpoint de getNotesForUser avec le userId {}", getNotesByUserRequest.getUserId());
        NoteList.Notes note = new NoteList.Notes();
        note.setId(1);
        note.setTitle("Le titre de la note");
        note.setContent("Un contenu pour la note");
        note.setOwner(1);
        note.setCreatedAt(LocalDateTime.now());
        note.setUpdatedAt(LocalDateTime.now());
        NoteList noteList = new NoteList();
        noteList.getNotes().add(note);

        if (1 == getNotesByUserRequest.getUserId()) {
            throw new NoteServiceException("L'user Id est egal à un et j'aime pas ça !!!");
        }

        return noteList;
    }

}
