package com.my.notes.ui.configuration;

import com.my.notes.ui.exceptions.BadCredentialsException;
import com.my.notes.ui.services.business.AuthServices;
import my_notes.notes.auth.UserBean;
import my_notes.notes.auth.UserPasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.ws.soap.client.SoapFaultClientException;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthProvider implements AuthenticationProvider {

    private final AuthServices authServices;

    public CustomAuthProvider(AuthServices authServices) {
        this.authServices = authServices;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserPasswordToken userPasswordToken = new UserPasswordToken();
        userPasswordToken.setIdentifier(authentication.getName());
        userPasswordToken.setPassword(authentication.getCredentials().toString());

        UserBean userBean;
        try {
            userBean = authServices.authenticateUser(userPasswordToken);
        } catch (SoapFaultClientException e) {
            throw new BadCredentialsException(e.getFaultStringOrReason(), e);
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        userBean.getRole().forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole())));

        return new UsernamePasswordAuthenticationToken(userPasswordToken.getIdentifier(),
                                                       userPasswordToken.getPassword(),
                                                       grantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
