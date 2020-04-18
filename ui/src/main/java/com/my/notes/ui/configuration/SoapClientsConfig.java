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

    @Bean("authMarshaller")
    public Jaxb2Marshaller authMarshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setContextPaths("my_notes.notes.standard", "my_notes.notes.auth");
        return jaxb2Marshaller;
    }

    @Bean("authSoapClient")
    public SoapClient authSoapClient(@Qualifier("authMarshaller") Jaxb2Marshaller jaxb2Marshaller) {
        SoapClient soapClient = new SoapClient();
        soapClient.setDefaultUri(authWsUrl);
        soapClient.setMarshaller(jaxb2Marshaller);
        soapClient.setUnmarshaller(jaxb2Marshaller);
        return soapClient;
    }

}
