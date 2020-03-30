package com.my.notes.notes.mappers;

import com.my.notes.notes.Note;
import com.my.notes.notes.NoteList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotesMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public NotesMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Note convertToNoteSoapModel(com.my.notes.notes.model.Note note) {
        return modelMapper.map(note, Note.class);
    }

    public NoteList.Notes convertToNotes(com.my.notes.notes.model.Note note) {
        return modelMapper.map(note, NoteList.Notes.class);
    }

    public com.my.notes.notes.model.Note convertToNoteModel(Note note) {
        return modelMapper.map(note, com.my.notes.notes.model.Note.class);
    }

}
