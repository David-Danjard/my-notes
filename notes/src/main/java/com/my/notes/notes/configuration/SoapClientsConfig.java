package com.my.notes.notes.configuration;

import com.my.notes.email.Email;
import com.my.notes.notes.services.soap.SoapClient;
import com.my.notes.notes.services.validation.XsdMessageValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class SoapClientsConfig {

    @Value("${services.soap.users.url}")
    private String usersWsUrl;
    @Value("${services.soap.users.username}")
    private String usersUserName;
    @Value("${services.soap.users.pwd}")
    private String usersPwd;

    @Value("${services.soap.mails.url}")
    private String mailsWsUrl;
    @Value("${services.soap.mails.username}")
    private String mailsUserName;
    @Value("${services.soap.mails.pwd}")
    private String mailsPwd;

    /* ---------------------- Users ---------------------- */

    @Bean("usersMarshaller")
    public Jaxb2Marshaller usersMarshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setContextPaths("com.my.notes.users");
        return jaxb2Marshaller;
    }

    @Bean("usersSoapClient")
    public SoapClient usersSoapClient(@Qualifier("usersMarshaller") Jaxb2Marshaller jaxb2Marshaller) {
        SoapClient soapClient = new SoapClient(usersUserName, usersPwd);
        soapClient.setDefaultUri(usersWsUrl);
        soapClient.setMarshaller(jaxb2Marshaller);
        soapClient.setUnmarshaller(jaxb2Marshaller);
        return soapClient;
    }

    /* ---------------------- Mails ---------------------- */

    @Bean("mailsMarshaller")
    public Jaxb2Marshaller mailsMarshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setContextPaths("com.my.notes.std", "com.my.notes.email");
        return jaxb2Marshaller;
    }

    @Bean("mailsSoapClient")
    public SoapClient mailsSoapClient(@Qualifier("mailsMarshaller") Jaxb2Marshaller jaxb2Marshaller) {
        SoapClient soapClient = new SoapClient(mailsUserName, mailsPwd, new XsdMessageValidator("xsd/email.xsd", Email.class));
        soapClient.setDefaultUri(mailsWsUrl);
        soapClient.setMarshaller(jaxb2Marshaller);
        soapClient.setUnmarshaller(jaxb2Marshaller);
        return soapClient;
    }

}
