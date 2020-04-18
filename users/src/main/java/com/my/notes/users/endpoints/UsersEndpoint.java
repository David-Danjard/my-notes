package com.my.notes.users.endpoints;

import com.my.notes.users.*;
import com.my.notes.users.exceptions.UserException;
import com.my.notes.users.services.UsersServices;
import com.my.notes.users.utils.SimpleMessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class UsersEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersEndpoint.class);
    private static final String XSD_NAMESPACE = "http://my-notes/notes/user";

    private UsersServices usersServices;

    @Autowired
    public UsersEndpoint(UsersServices usersServices) {
        this.usersServices = usersServices;
    }

    @PayloadRoot(namespace = XSD_NAMESPACE, localPart = "userListRequest")
    @ResponsePayload
    public UserList getUsers(@RequestPayload UserListRequest userListRequest){
        UserList userList = new UserList();
        userList.getUserBean().addAll(
                usersServices.getUsers()
        );
        return userList;
    }

    @PayloadRoot(namespace = XSD_NAMESPACE, localPart = "userBeanCreationRequest")
    @ResponsePayload
    public SimpleMessage createUser(@RequestPayload UserBeanCreationRequest userBeanRequest){
        UserBean userBean = userBeanRequest.getUserBean();
        LOGGER.info("Appel du endpoint de création d'un utilisateur : {} {}", userBean.getName(), userBean.getSurname());
        this.usersServices.createUser(userBean);
        return SimpleMessageUtils.getSuccessResponse(userBean.getName() + " " + userBean.getSurname() + " créé.");
    }

    @PayloadRoot(namespace = XSD_NAMESPACE, localPart = "userBeanUpdateRequest")
    @ResponsePayload
    public SimpleMessage updateUser(@RequestPayload UserBeanUpdateRequest userBeanRequest) throws UserException {
        UserBean userBean = userBeanRequest.getUserBean();
        LOGGER.info("Appel du endpoint de mise à jour d'un utilisateur {} {}", userBean.getName(), userBean.getSurname());
        this.usersServices.updateUser(userBean);
        return SimpleMessageUtils.getSuccessResponse("Utilisateur " + userBean.getName() + " " + userBean.getSurname() + " mis à jour.");
    }

    @PayloadRoot(namespace = XSD_NAMESPACE, localPart = "deleteUserRequest")
    @ResponsePayload
    public SimpleMessage deleteUser(@RequestPayload DeleteUserRequest deleteUserRequest){
        LOGGER.info("Appel sur le endpoint de suppression d'un utilisateur {}", deleteUserRequest.getId());
        this.usersServices.deleteUser(deleteUserRequest.getId());
        return SimpleMessageUtils.getSuccessResponse("User " + deleteUserRequest.getId() + " supprimé.");
    }

}
