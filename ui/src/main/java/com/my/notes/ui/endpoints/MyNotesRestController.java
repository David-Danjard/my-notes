package com.my.notes.ui.endpoints;

import com.my.notes.ui.repository.NotesRepo;
import com.my.notes.ui.services.business.NotesServices;
import com.my.notes.ui.services.business.UsersServices;
import generated.Note;
import generated.SimpleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notes")
public class MyNotesRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyNotesRestController.class);
    private final NotesRepo notesRepo;
    private final NotesServices notesServices;
    private final UsersServices usersServices;

    public MyNotesRestController(NotesRepo notesRepo, NotesServices notesServices, UsersServices usersServices) {
        this.notesRepo = notesRepo;
        this.notesServices = notesServices;
        this.usersServices = usersServices;
    }

    @PostMapping("/add")
    public ResponseEntity<Note> addNote(Note note, Authentication authentication) {
        note.setOwner(
                usersServices.getUserByEmail(authentication.getName()).getId()
        );
        notesServices.addNote(note);
        return ResponseEntity.ok(note);
    }

    @GetMapping("/share/{note-id}/{user-id}")
    public void shareNote(@PathVariable("note-id") int noteId,
                          @PathVariable("user-id") int userId) {
        SimpleResponse simpleResponse = notesServices.shareNote(userId, noteId);
        LOGGER.info("Share note {} with {} status {} / message {}", noteId, userId, simpleResponse.getStatus(), simpleResponse.getMessage());
    }

    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable("id") int id) {
        notesRepo.deleteById(id);
    }

    @PutMapping("/update")
    public void updateNote(Note note) {
        //notesRepo.updateById(note);
    }

}
