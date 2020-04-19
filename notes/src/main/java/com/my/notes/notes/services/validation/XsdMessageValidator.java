package com.my.notes.notes.services.validation;

import com.my.notes.notes.exceptions.SoapMessageValidationException;
import org.springframework.core.io.ClassPathResource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.util.JAXBSource;
import javax.xml.transform.Source;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;

public class XsdMessageValidator implements SoapMessageValidator {

    private final String xsdPath;
    private final Class<?> requestType;

    public XsdMessageValidator(String xsdPath, Class<?> requestType) {
        this.xsdPath = xsdPath;
        this.requestType = requestType;
    }

    @Override
    public void validateMessage(Object message) throws SoapMessageValidationException {
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            Schema schema = sf.newSchema(new ClassPathResource(xsdPath).getFile());
            Validator validator = schema.newValidator();
            validator.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            validator.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
            Source source = new JAXBSource(JAXBContext.newInstance(requestType), message);
            validator.validate(source);
        } catch (SAXException | IOException | JAXBException e) {
            throw new SoapMessageValidationException("Invalid message " + e.getMessage(), e);
        }
    }
}
