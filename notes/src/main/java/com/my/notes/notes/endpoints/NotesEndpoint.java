package com.my.notes.notes.endpoints;

import com.my.notes.notes.*;
import com.my.notes.notes.exceptions.NoteNotFoundException;
import com.my.notes.notes.mappers.NotesMapper;
import com.my.notes.notes.services.NoteServices;
import com.my.notes.notes.utils.SimpleResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.server.endpoint.annotation.SoapAction;

@Endpoint
public class NotesEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotesEndpoint.class);

    private final NoteServices noteServices;
    private final NotesMapper notesMapper;

    @Autowired
    public NotesEndpoint(NoteServices noteServices, NotesMapper notesMapper) {
        this.noteServices = noteServices;
        this.notesMapper = notesMapper;
    }

    @SoapAction("/getNote")
    @ResponsePayload
    public Note getNoteById(@RequestPayload GetNoteRequest getNoteRequest) throws NoteNotFoundException {
        LOGGER.info("Appel reçu sur le endpoint de get note pour la note : {}", getNoteRequest.getId());
        return notesMapper.convertToNoteSoapModel(
                noteServices.findNote(getNoteRequest.getId())
        );
    }

    @SoapAction("/getNotesByUser")
    @ResponsePayload
    public NoteList getNotesForUser(@RequestPayload GetNotesByUserRequest getNotesByUserRequest) {
        LOGGER.info("Appel reçu sur le endpoint de getNotesForUser avec le userId {}", getNotesByUserRequest.getUserId());
        NoteList noteList = new NoteList();
        noteServices.findNotesForUser(
                getNotesByUserRequest.getUserId()
        ).forEach(note -> noteList.getNotes().add(
                notesMapper.convertToNotes(note)
        ));
        return noteList;
    }

    @SoapAction("/addNote")
    @ResponsePayload
    public SimpleResponse addNote(@RequestPayload Note note) {
        LOGGER.info("Appel sur le endpoint de creation de note pour la note titree : {}", note.getTitle());
        noteServices.addNote(
                notesMapper.convertToNoteModel(note)
        );
        return SimpleResponseUtils.getSuccessResponse("Note cree avec success");
    }

    @SoapAction("/NoteUpdate")
    @ResponsePayload
    public SimpleResponse updateNote(@RequestPayload NoteUpdate noteUpdate) throws NoteNotFoundException {
        LOGGER.info("Appel sur le endpoint de mise à jour des notes, avec le titre : {}", noteUpdate.getTitle());
        noteServices.updateNote(noteUpdate);
        return SimpleResponseUtils.getSuccessResponse("Note " + noteUpdate.getId() + " mise à jour.");
    }

    @SoapAction("/deleteNote")
    @ResponsePayload
    public SimpleResponse deleteNote(@RequestPayload DeleteNoteRequest deleteNoteRequest) {
        LOGGER.info("Appel sur le endpoint de suppression d'une note, pour la note id : {}", deleteNoteRequest.getId());
        noteServices.deleteNote(deleteNoteRequest.getId());
        return SimpleResponseUtils.getSuccessResponse("Note " + deleteNoteRequest.getId() + " supprimee.");
    }

    @SoapAction("/shareNote")
    @ResponsePayload
    public SimpleResponse shareNote(@RequestPayload ShareNoteRequest shareNoteRequest) throws NoteNotFoundException {
        LOGGER.info("Appel sur le endpoint de partage d'une note, pour la note {} vers l'utilisateur {}", shareNoteRequest.getNoteId(), shareNoteRequest.getUserId());
        noteServices.shareNote(shareNoteRequest.getNoteId(), shareNoteRequest.getUserId());
        return SimpleResponseUtils.getSuccessResponse("Note  " + shareNoteRequest.getNoteId() + " envoyée à l'utilisateur " + shareNoteRequest.getUserId());
    }

}
