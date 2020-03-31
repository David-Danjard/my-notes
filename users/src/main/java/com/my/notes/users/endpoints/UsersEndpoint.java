package com.my.notes.users.endpoints;

import com.my.notes.users.*;
import com.my.notes.users.utils.SimpleMessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class UsersEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersEndpoint.class);
    private static final String XSD_NAMESPACE = "http://my-notes/notes/user";

    @PayloadRoot(namespace = XSD_NAMESPACE, localPart = "userBeanCreationRequest")
    @ResponsePayload
    public SimpleMessage createUser(@RequestPayload UserBeanCreationRequest userBeanRequest){
        UserBean userBean = userBeanRequest.getUserBean();
        LOGGER.info("Appel du endpoint de création d'un utilisateur : {} {}", userBean.getName(), userBean.getSurname());
        // TODO Ici sera la logique métier
        return SimpleMessageUtils.getSuccessResponse(userBean.getName() + " " + userBean.getSurname() + " créé.");
    }

    @PayloadRoot(namespace = XSD_NAMESPACE, localPart = "userBeanUpdateRequest")
    @ResponsePayload
    public SimpleMessage updateUser(@RequestPayload UserBeanUpdateRequest userBeanRequest){
        UserBean userBean = userBeanRequest.getUserBean();
        LOGGER.info("Appel du endpoint de mise à jour d'un utilisateur {} {}", userBean.getName(), userBean.getSurname());
        // TODO ici sera la logique métier
        return SimpleMessageUtils.getSuccessResponse("Utilisateur " + userBean.getName() + " " + userBean.getSurname() + " mis à jour.");
    }

    @PayloadRoot(namespace = XSD_NAMESPACE, localPart = "deleteUserRequest")
    @ResponsePayload
    public SimpleMessage deleteUser(@RequestPayload DeleteUserRequest deleteUserRequest){
        LOGGER.info("Appel sur le endpoint de suppression d'un utilisateur {}", deleteUserRequest.getId());
        // TODO ici sera la logique métier
        return SimpleMessageUtils.getSuccessResponse("User " + deleteUserRequest.getId() + " supprimé.");
    }

}
