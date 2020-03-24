//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.7 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.03.24 à 10:06:20 PM CET 
//


package com.my.notes.notes;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.my.notes.notes package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _NoteListOwner_QNAME = new QName("", "owner");
    private final static QName _NoteListCreatedAt_QNAME = new QName("", "createdAt");
    private final static QName _NoteListId_QNAME = new QName("", "id");
    private final static QName _NoteListTitle_QNAME = new QName("", "title");
    private final static QName _NoteListContent_QNAME = new QName("", "content");
    private final static QName _NoteListUpdatedAt_QNAME = new QName("", "updatedAt");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.my.notes.notes
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ShareNoteRequest }
     * 
     */
    public ShareNoteRequest createShareNoteRequest() {
        return new ShareNoteRequest();
    }

    /**
     * Create an instance of {@link NoteUpdate }
     * 
     */
    public NoteUpdate createNoteUpdate() {
        return new NoteUpdate();
    }

    /**
     * Create an instance of {@link NoteList }
     * 
     */
    public NoteList createNoteList() {
        return new NoteList();
    }

    /**
     * Create an instance of {@link SimpleResponse }
     * 
     */
    public SimpleResponse createSimpleResponse() {
        return new SimpleResponse();
    }

    /**
     * Create an instance of {@link Note }
     * 
     */
    public Note createNote() {
        return new Note();
    }

    /**
     * Create an instance of {@link Fault }
     * 
     */
    public Fault createFault() {
        return new Fault();
    }

    /**
     * Create an instance of {@link GetNotesByUserRequest }
     * 
     */
    public GetNotesByUserRequest createGetNotesByUserRequest() {
        return new GetNotesByUserRequest();
    }

    /**
     * Create an instance of {@link DeleteNoteRequest }
     * 
     */
    public DeleteNoteRequest createDeleteNoteRequest() {
        return new DeleteNoteRequest();
    }

    /**
     * Create an instance of {@link GetNoteRequest }
     * 
     */
    public GetNoteRequest createGetNoteRequest() {
        return new GetNoteRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "owner", scope = NoteList.class)
    public JAXBElement<Integer> createNoteListOwner(Integer value) {
        return new JAXBElement<Integer>(_NoteListOwner_QNAME, Integer.class, NoteList.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "createdAt", scope = NoteList.class)
    public JAXBElement<XMLGregorianCalendar> createNoteListCreatedAt(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_NoteListCreatedAt_QNAME, XMLGregorianCalendar.class, NoteList.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "id", scope = NoteList.class)
    public JAXBElement<Integer> createNoteListId(Integer value) {
        return new JAXBElement<Integer>(_NoteListId_QNAME, Integer.class, NoteList.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "title", scope = NoteList.class)
    public JAXBElement<String> createNoteListTitle(String value) {
        return new JAXBElement<String>(_NoteListTitle_QNAME, String.class, NoteList.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "content", scope = NoteList.class)
    public JAXBElement<String> createNoteListContent(String value) {
        return new JAXBElement<String>(_NoteListContent_QNAME, String.class, NoteList.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "updatedAt", scope = NoteList.class)
    public JAXBElement<XMLGregorianCalendar> createNoteListUpdatedAt(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_NoteListUpdatedAt_QNAME, XMLGregorianCalendar.class, NoteList.class, value);
    }

}
