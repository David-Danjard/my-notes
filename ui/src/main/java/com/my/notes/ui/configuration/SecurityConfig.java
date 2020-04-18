package com.my.notes.ui.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        http.headers().disable()
            .httpBasic().and()
            .authorizeRequests().antMatchers("/login", "/css/**", "/js/**", "/logout").permitAll()
                                .antMatchers("/", "/api/**").hasRole("USER")
                                .anyRequest().denyAll()
            .and().formLogin()
                  .loginPage("/login")
                  .loginProcessingUrl("/login")
                  .defaultSuccessUrl("/", true)
                  .failureUrl("/login?error=true")
            .and().logout()
                  .logoutUrl("/logout")
                  .deleteCookies("JSESSIONID");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
