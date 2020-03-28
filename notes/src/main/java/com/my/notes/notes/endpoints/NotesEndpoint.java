package com.my.notes.notes.endpoints;

import com.my.notes.notes.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.server.endpoint.annotation.SoapAction;

@Endpoint
public class NotesEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotesEndpoint.class);

    @SoapAction("/getNote")
    @ResponsePayload
    public Note getNoteById(@RequestPayload GetNoteRequest getNoteRequest){
        LOGGER.info("Appel reçu sur le sendpoint de get note pour la note : {}", getNoteRequest.getId());
        // TODO ici on déclanchera la logique métier
        return new Note();
    }

    @SoapAction("/getNotesByUser")
    @ResponsePayload
    public NoteList getNotesForUser(@RequestPayload GetNotesByUserRequest getNotesByUserRequest) {
        LOGGER.info("Appel reçu sur le endpoint de getNotesForUser avec le userId {}", getNotesByUserRequest.getUserId());
        // TODO ici on déclanchera la logique métier
        return new NoteList();
    }

    @SoapAction("/addNote")
    @ResponsePayload
    public SimpleResponse addNote(@RequestPayload Note note) {
        LOGGER.info("Appel sur le endpoint de creation de note pour la note titree : {}", note.getTitle());
        // TODO ici on déclanchera la logique métier
        return new SimpleResponse();
    }

    @SoapAction("/noteUpdate")
    @ResponsePayload
    public SimpleResponse updateNote(@RequestPayload NoteUpdate noteUpdate) {
        LOGGER.info("Appel sur le endpoint de mise à jour des notes, avec le titre : {}", noteUpdate.getTitle());
        // TODO ici on déclanchera la logique métier
        return new SimpleResponse();
    }

    @SoapAction("/deleteNote")
    @ResponsePayload
    public SimpleResponse deleteNote(@RequestPayload DeleteNoteRequest deleteNoteRequest) {
        LOGGER.info("Appel sur le endpoint de suppression d'une note, pour la note id : {}", deleteNoteRequest.getId());
        // TODO ici on déclanchera la logique métier
        return new SimpleResponse();
    }

    @SoapAction("/shareNote")
    @ResponsePayload
    public SimpleResponse shareNote(@RequestPayload ShareNoteRequest shareNoteRequest) {
        LOGGER.info("Appel sur le endpoint de partage d'une note, pour la note {} vers l'utilisateur {}", shareNoteRequest.getNoteId(), shareNoteRequest.getUserId());
        // TODO ici on déclanchera la logique métier
        return new SimpleResponse();
    }

}
