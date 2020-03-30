package com.my.notes.notes.repository;

import com.my.notes.notes.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Integer> {
    Optional<List<Note>> findAllByOwner(int ownerId);
}
