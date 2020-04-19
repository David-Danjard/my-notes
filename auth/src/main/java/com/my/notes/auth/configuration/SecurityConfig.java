package com.my.notes.auth.configuration;

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
            .antMatchers("/auth-service/ws/AUTH_SERVICES.wsdl", "/auth-service/ws/xsdSchema.xsd")
                .permitAll()
            .antMatchers("/auth-service/ws/**")
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

