package com.my.notes.mails.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        SimpleUrlAuthenticationSuccessHandler successHandler
                = new SimpleUrlAuthenticationSuccessHandler();
        successHandler.setUseReferer(true);

        http.authorizeRequests()
            .antMatchers("/email-service/ws/MAILS_SERVICES.wsdl", "/email-service/ws/email.xsd", "/email-service/ws/standard.xsd")
                .permitAll()
            .antMatchers("/email-service/ws/**")
                    .hasRole("TECHNICAL")
            .antMatchers("/**").denyAll()
            .and().httpBasic()
                .authenticationEntryPoint(getBasicAuthEntryPoint())
            .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().csrf().disable()
        ;

    }

    @Bean
    public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint(){
        return new CustomBasicAuthenticationEntryPoint();
    }

}

