package com.my.notes.notes.services.validation;

import com.my.notes.notes.exceptions.SoapMessageValidationException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptorAdapter;
import org.springframework.ws.context.MessageContext;
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

public class XsdValidationClientInterceptor extends ClientInterceptorAdapter {

    private final String xsdPath;

    public XsdValidationClientInterceptor(String xsdPath) {
        this.xsdPath = xsdPath;
    }

    @Override
    public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            Schema schema = sf.newSchema(new ClassPathResource(xsdPath).getFile());
            Validator validator = schema.newValidator();
            validator.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            validator.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
            Source source = messageContext.getRequest().getPayloadSource();
            validator.validate(source);
        } catch (SAXException | IOException e) {
            throw new SoapMessageValidationException("Invalid message " + e.getMessage(), e);
        }
        return super.handleRequest(messageContext);
    }
}
