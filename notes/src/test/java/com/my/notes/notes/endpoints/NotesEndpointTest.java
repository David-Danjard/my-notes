package com.my.notes.notes.endpoints;

import com.my.notes.notes.GetNotesByUserRequest;
import com.my.notes.notes.NoteList;
import com.my.notes.notes.model.Note;
import com.my.notes.notes.repository.NoteRepository;
import com.my.notes.notes.services.soap.SoapClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NotesEndpointTest {

    private static final String SERVICE_USER = "TechnicalUser";
    private static final String SERVICE_PWD = "Passw0rd";
    private static final String GET_NOTES_BY_USER_REQUEST =
            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "   <soapenv:Header/>\n" +
            "   <soapenv:Body>\n" +
            "      <GetNotesByUserRequest>\n" +
            "         <userId>1</userId>\n" +
            "      </GetNotesByUserRequest>\n" +
            "   </soapenv:Body>\n" +
            "</soapenv:Envelope>";

    @LocalServerPort
    private int port;

    @Autowired
    private NotesEndpoint notesEndpoint;

    @Autowired
    private TestRestTemplate restTemplate;
    @MockBean
    private NoteRepository noteRepository;

    @BeforeEach
    public void init() {
        Note note = new Note();
        note.setId(1);
        List<Note> notes = new ArrayList<>();
        notes.add(note);
        Mockito.when(noteRepository.findAllByOwner(1))
                .thenReturn(Optional.of(notes));
    }

    /**
     * Test très basique considérable comme un petit test unitaire
     */
    @Test
    public void getNotesForUserAutowiredTestCase() {
        GetNotesByUserRequest getNotesByUserRequest = new GetNotesByUserRequest();
        getNotesByUserRequest.setUserId(1);

        NoteList noteList = notesEndpoint.getNotesForUser(getNotesByUserRequest);
        assertFalse(noteList.getNotes().isEmpty());
        assertEquals(1, noteList.getNotes().get(0).getId());
    }

    /**
     * Test plus complet à utiliser comme test d'intégration par exemple
     */
    @Test
    public void getNotesForUserSoapClientTestCase() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setContextPaths("com.my.notes.notes");
        SoapClient soapClient = new SoapClient(SERVICE_USER, SERVICE_PWD);
        soapClient.setDefaultUri("http://localhost:"+port+"/notes-service/ws");
        soapClient.setMarshaller(jaxb2Marshaller);
        soapClient.setUnmarshaller(jaxb2Marshaller);

        GetNotesByUserRequest getNotesByUserRequest = new GetNotesByUserRequest();
        getNotesByUserRequest.setUserId(1);

        NoteList noteList = (NoteList) soapClient.callWebService(getNotesByUserRequest, new SoapActionCallback("/getNotesByUser"));
        assertFalse(noteList.getNotes().isEmpty());
        assertEquals(1, noteList.getNotes().get(0).getId());
    }

    /**
     * Test plus complet à utiliser comme test d'intégration par exemple
     * Un service SOAP, peut aussi se tester comme une API rest
     */
    @Test
    public void getNotesForUserRestTemplateTestCase() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_XML);
        httpHeaders.setBasicAuth(SERVICE_USER, SERVICE_PWD);
        httpHeaders.add("SOAPAction", "/getNotesByUser");

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:"+port+"/notes-service/ws",
                HttpMethod.POST,
                new HttpEntity<>(GET_NOTES_BY_USER_REQUEST, httpHeaders),
                String.class
        );
        System.out.println("Response : " + response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

}