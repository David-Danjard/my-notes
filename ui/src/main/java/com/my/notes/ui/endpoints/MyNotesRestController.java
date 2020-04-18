package com.my.notes.ui.endpoints;

import com.my.notes.ui.model.Note;
import com.my.notes.ui.repository.NotesRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notes")
public class MyNotesRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyNotesRestController.class);
    private final NotesRepo notesRepo;

    public MyNotesRestController(NotesRepo notesRepo) {
        this.notesRepo = notesRepo;
    }

    @PostMapping("/add")
    public ResponseEntity<Note> addNote(Note note) {
        notesRepo.addNote(note);
        return ResponseEntity.ok(note);
    }

    @GetMapping("/share/{note-id}/{user-id}")
    public void shareNote(@PathVariable("note-id") int noteId,
                          @PathVariable("user-id") int userId) {
        // Nothing todo now
        LOGGER.info("Share note {} with {}", noteId, userId);
    }

    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable("id") int id) {
        notesRepo.deleteById(id);
    }

    @PutMapping("/update")
    public void updateNote(Note note) {
        notesRepo.updateById(note);
    }

}
