package com.my.notes.notes.services.soap;

import org.springframework.ws.transport.http.HttpUrlConnectionMessageSender;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Base64;

public class CustomWebServiceMessageSender extends HttpUrlConnectionMessageSender {

    private String userName;
    private String pwd;

    public CustomWebServiceMessageSender(String userName, String pwd) {
        this.userName = userName;
        this.pwd = pwd;
    }

    @Override
    protected void prepareConnection(HttpURLConnection connection) throws IOException {
        // Basic Auth = HTTP Header : name -> Authorization / value -> "Basic BASE64([username:pwd])"
        String userPwd = userName + ":" + pwd;
        String encodedAuth = Base64.getEncoder().encodeToString(userPwd.getBytes());
        connection.setRequestProperty("Authorization", "Basic "+encodedAuth);
        super.prepareConnection(connection);
    }
}
