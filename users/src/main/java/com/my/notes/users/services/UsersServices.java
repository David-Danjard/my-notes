package com.my.notes.users.services;

import com.my.notes.users.UserBean;
import com.my.notes.users.UserBeanUpdateRequest;
import com.my.notes.users.exceptions.UserException;
import com.my.notes.users.mappers.UserMapper;
import com.my.notes.users.model.User;
import com.my.notes.users.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersServices {

    private UsersRepository usersRepository;
    private UserMapper userMapper;

    @Autowired
    public UsersServices(UsersRepository usersRepository, UserMapper userMapper) {
        this.usersRepository = usersRepository;
        this.userMapper = userMapper;
    }

    public void createUser(UserBean userBean) {
        usersRepository.saveAndFlush(
                userMapper.convertToUserModel(userBean)
        );
    }

    public void deleteUser(int userId) {
        usersRepository.deleteById(userId);
    }

    public void updateUser(UserBean userBean) throws UserException {
        User user = usersRepository.findById(userBean.getId())
                .orElseThrow(() -> new UserException("L'utilisateur " + userBean.getId() + " n'existe pas."));
        user.setEmail(userBean.getEmail());
        user.setPassword(userBean.getPassword());
        user.setName(userBean.getName());
        user.setSurname(userBean.getSurname());
        usersRepository.saveAndFlush(user);
    }

}
