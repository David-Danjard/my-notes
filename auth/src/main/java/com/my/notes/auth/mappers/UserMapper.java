package com.my.notes.auth.mappers;

import com.my.notes.auth.Role;
import com.my.notes.auth.UserBean;
import com.my.notes.auth.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    private ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserBean convertToUserBean(User user) {
        UserBean userBean = modelMapper.map(user, UserBean.class);
        user.getRole().forEach(role -> userBean.getRole().add(convertToRole(role)));
        return userBean;
    }

    public Role convertToRole(com.my.notes.auth.model.Role role) {
        return modelMapper.map(role, Role.class);
    }

}
