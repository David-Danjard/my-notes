package com.my.notes.notes.services;

import com.my.notes.notes.NoteUpdate;
import com.my.notes.notes.exceptions.NoteNotFoundException;
import com.my.notes.notes.model.Note;
import com.my.notes.notes.repository.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NoteServices {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoteServices.class);
    private final NoteRepository noteRepository;

    @Autowired
    public NoteServices(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Note findNote(int noteId) throws NoteNotFoundException {
        Optional<Note> optionalNote = noteRepository.findById(noteId);
        return optionalNote.orElseThrow(
                () -> new NoteNotFoundException("La note " + noteId + " n'existe pas.")
        );
    }

    public List<Note> findNotesForUser(int userId) {
        Optional<List<Note>> optionalNoteList = noteRepository.findAllByOwner(userId);
        return optionalNoteList.orElse(new ArrayList<>());
    }

    public void addNote(Note note) {
        note.setCreatedAt(LocalDateTime.now());
        note.setUpdatedAt(LocalDateTime.now());
        noteRepository.save(note);
    }

    public void updateNote(NoteUpdate noteUpdate) throws NoteNotFoundException {
        Optional<Note> oldNoteOptional = noteRepository.findById(noteUpdate.getId());
        Note oldNote = oldNoteOptional.orElseThrow(
                () -> new NoteNotFoundException("La note " + noteUpdate.getId() + " n'existe pas.")
        );
        oldNote.setTitle(noteUpdate.getTitle());
        oldNote.setContent(noteUpdate.getContent());
        oldNote.setOwner(noteUpdate.getOwner());
        oldNote.setUpdatedAt(LocalDateTime.now());
        noteRepository.saveAndFlush(oldNote);
    }

    public void deleteNote(int noteId) {
        noteRepository.deleteById(noteId);
    }

    public void shareNote(int noteId, int userId) throws NoteNotFoundException {
        Optional<Note> optionalNote = noteRepository.findById(noteId);
        Note note = optionalNote.orElseThrow(
                () -> new NoteNotFoundException("La note " + noteId + " n'existe pas.")
        );
        // TODO récupérer l'email de l'utilisateur, afin de lui envoyer un email
        // TODO préparer et envoyer l'email quand le service sera développé
        LOGGER.info("Note : {}", note.getTitle());
    }

}
