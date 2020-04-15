package com.my.notes.auth.configuration;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig {

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/auth-service/ws/*");
    }

    @Bean(name = "AUTH_SERVICES")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema authSchema){
        DefaultWsdl11Definition defaultWsdl11Definition = new DefaultWsdl11Definition();
        defaultWsdl11Definition.setPortTypeName("AuthPort");
        defaultWsdl11Definition.setTargetNamespace("http://my-notes/notes/auth");
        defaultWsdl11Definition.setLocationUri("/auth-service/ws");
        defaultWsdl11Definition.setSchema(authSchema);
        return defaultWsdl11Definition;
    }

    @Bean
    public XsdSchema authSchema(){
        return new SimpleXsdSchema(new ClassPathResource("xsd/auth.xsd"));
    }

}
