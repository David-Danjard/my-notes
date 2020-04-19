package com.my.notes.notes.services.business;

import com.my.notes.email.Email;
import com.my.notes.notes.NoteUpdate;
import com.my.notes.notes.exceptions.NoteNotFoundException;
import com.my.notes.notes.exceptions.NoteServiceException;
import com.my.notes.notes.model.Note;
import com.my.notes.notes.repository.NoteRepository;
import com.my.notes.notes.services.soap.SoapClient;
import com.my.notes.users.UserBean;
import com.my.notes.users.UserList;
import com.my.notes.users.UserListRequest;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ws.soap.addressing.client.ActionCallback;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class NoteServices {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoteServices.class);
    private final NoteRepository noteRepository;
    private final SoapClient usersSoapClient;
    private final SoapClient mailsSoapClient;
    private final TemplateEngine templateEngine;

    @Autowired
    public NoteServices(NoteRepository noteRepository,
                        @Qualifier("usersSoapClient") SoapClient usersSoapClient,
                        @Qualifier("mailsSoapClient") SoapClient mailsSoapClient,
                        TemplateEngine templateEngine) {
        this.noteRepository = noteRepository;
        this.usersSoapClient = usersSoapClient;
        this.mailsSoapClient = mailsSoapClient;
        this.templateEngine = templateEngine;
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
        if (noteUpdate.getOwner() > 0) {
            // we update the owner just if it is asked
            oldNote.setOwner(noteUpdate.getOwner());
        }
        oldNote.setUpdatedAt(LocalDateTime.now());
        noteRepository.saveAndFlush(oldNote);
    }

    public void deleteNote(int noteId) {
        noteRepository.deleteById(noteId);
    }

    public void shareNote(int noteId, int userId) throws NoteServiceException {
        Optional<Note> optionalNote = noteRepository.findById(noteId);
        Note note = optionalNote.orElseThrow(
                () -> new NoteNotFoundException("La note " + noteId + " n'existe pas.")
        );
        // récupération de l'email de l'utilisateur, afin de lui envoyer un email => appel SOAP sur Users Service
        UserListRequest userListRequest = new UserListRequest();
        userListRequest.setUserId(userId);

        UserList userList = (UserList) usersSoapClient.callWebService(userListRequest, new SoapActionCallback("http://my-notes/notes/user/UsersPort/userListRequest"));

        UserBean userBean = userList.getUserBean().stream()
                                                    .findFirst().orElseThrow(
                        () -> new NoteServiceException("Utilisateur " + userId + " introuvable")
                );
        LOGGER.info("User found : {} {}", userBean.getName(), userBean.getSurname());

        // préparation et envoi de l'email
        Context context = new Context();
        context.setVariable("title", note.getTitle());
        context.setVariable("content", note.getContent());
        Email email = new Email();
        email.setFrom("my-notes@monmail.com");
        email.setTo(userBean.getEmail());
        email.setSubject(userBean.getName() + " " + userBean.getSurname() + " souhaiterai partager une note avec vous.");
        email.setContent(
                Base64.getEncoder().encodeToString(
                        templateEngine.process("shareNoteTemplate", context).getBytes()
                )
        );

        try {
            mailsSoapClient.callWebService(email, new ActionCallback("/html-send"));
        } catch (URISyntaxException e) {
            throw new NoteServiceException(e);
        }

        LOGGER.info("Note : {}", note.getTitle());
    }

}
