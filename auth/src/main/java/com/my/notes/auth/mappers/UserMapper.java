package com.my.notes.auth.mappers;

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
        return modelMapper.map(user, UserBean.class);
    }

}
