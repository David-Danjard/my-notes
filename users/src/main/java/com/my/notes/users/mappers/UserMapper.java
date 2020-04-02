package com.my.notes.users.mappers;

import com.my.notes.users.UserBean;
import com.my.notes.users.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    private ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User convertToUserModel(UserBean userBean) {
        return modelMapper.map(userBean, User.class);
    }

    public UserBean convertToUserBean(User user) {
        return modelMapper.map(user, UserBean.class);
    }

}
