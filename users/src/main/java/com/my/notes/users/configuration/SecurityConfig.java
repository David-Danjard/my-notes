package com.my.notes.users.configuration;

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
            .antMatchers("/users-service/ws/USERS_SERVICES.wsdl", "/users-service/ws/xsdSchema.xsd")
                .permitAll()
            .antMatchers("/users-service/ws/**")
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

