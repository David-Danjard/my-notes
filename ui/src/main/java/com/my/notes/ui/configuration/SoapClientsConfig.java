package com.my.notes.ui.configuration;

import com.my.notes.ui.services.soap.SoapClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class SoapClientsConfig {

    @Value("${services.soap.auth.url}")
    private String authWsUrl;
    @Value("${services.soap.auth.username}")
    private String authUserName;
    @Value("${services.soap.auth.pwd}")
    private String authPwd;

    @Value("${services.soap.notes.url}")
    private String notesWsUrl;
    @Value("${services.soap.notes.username}")
    private String notesUserName;
    @Value("${services.soap.notes.pwd}")
    private String notesPwd;

    /* -------------------- Authentication -------------------- */

    @Bean("authMarshaller")
    public Jaxb2Marshaller authMarshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setContextPaths("my_notes.notes.standard", "my_notes.notes.auth");
        return jaxb2Marshaller;
    }

    @Bean("authSoapClient")
    public SoapClient authSoapClient(@Qualifier("authMarshaller") Jaxb2Marshaller jaxb2Marshaller) {
        SoapClient soapClient = new SoapClient(authUserName, authPwd);
        soapClient.setDefaultUri(authWsUrl);
        soapClient.setMarshaller(jaxb2Marshaller);
        soapClient.setUnmarshaller(jaxb2Marshaller);
        return soapClient;
    }

    /* ------------------------ Notes ------------------------ */

    @Bean("notesMarshaller")
    public Jaxb2Marshaller notesMarshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setContextPaths("generated");
        return jaxb2Marshaller;
    }

    @Bean("notesSoapClient")
    public SoapClient notesSoapClient(@Qualifier("notesMarshaller") Jaxb2Marshaller jaxb2Marshaller) {
        SoapClient soapClient = new SoapClient(notesUserName, notesPwd);
        soapClient.setDefaultUri(notesWsUrl);
        soapClient.setMarshaller(jaxb2Marshaller);
        soapClient.setUnmarshaller(jaxb2Marshaller);
        return soapClient;
    }

}
