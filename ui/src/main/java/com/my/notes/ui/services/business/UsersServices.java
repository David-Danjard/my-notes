package com.my.notes.ui.services.business;

import com.my.notes.ui.services.soap.SoapClient;
import my_notes.notes.user.UserList;
import my_notes.notes.user.UserListRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ws.soap.client.core.SoapActionCallback;

@Service
public class UsersServices {

    private final SoapClient usersSoapClient;

    @Autowired
    public UsersServices(@Qualifier("usersSoapClient") SoapClient usersSoapClient) {
        this.usersSoapClient = usersSoapClient;
    }

    public UserList getUsers() {
        return (UserList) usersSoapClient.callWebService(new UserListRequest(), new SoapActionCallback("http://my-notes/notes/user/UsersPort/userListRequest"));
    }

}
