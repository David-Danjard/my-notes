package com.my.notes.ui.repository;

import com.my.notes.ui.model.Note;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NotesRepo {

    private List<Note> notes;

    @PostConstruct
    public void init() {
        notes = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Note note = new Note();
            note.setId(i);
            note.setTitle("Note "+i+" title");
            note.setContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus diam metus, porta at ante id, congue facilisis tellus. Suspendisse dictum ut mi in euismod. Sed faucibus luctus massa eu pretium. Donec blandit luctus enim, eget mattis nibh ultricies nec. Sed convallis, dolor quis gravida feugiat, orci justo tincidunt nunc, quis placerat turpis tortor fringilla risus. Nunc iaculis mi a turpis sollicitudin, vitae cursus sapien ornare. Etiam varius et nisl eu hendrerit. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Cras egestas, purus mattis faucibus blandit, risus mi sagittis lorem, a euismod elit odio ac quam. Quisque finibus velit quis bibendum bibendum. Mauris enim lectus, efficitur lobortis feugiat id, consequat ultrices lorem. Morbi malesuada odio sapien, non cursus est sodales eu. Aliquam risus neque, rutrum vel rutrum a, tempor quis enim.");
            notes.add(note);
        }
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void addNote(Note note) {
        note.setId(notes.size());
        note.setCreatedAt(LocalDateTime.now());
        note.setUpdatedAt(LocalDateTime.now());
        notes.add(note);
    }

    public void updateById(Note note) {
        notes.forEach(
                n -> {
                    if (n.getId() == note.getId()) {
                        n.setTitle(note.getTitle());
                        n.setContent(note.getContent());
                    }
                }
        );
    }

    public void deleteById(int id) {
        Optional<Note> noteOptional = notes.stream().filter(n -> id == n.getId())
                .findFirst();
        if (noteOptional.isPresent()) {
            notes.remove(noteOptional.get());
        }
    }
}
