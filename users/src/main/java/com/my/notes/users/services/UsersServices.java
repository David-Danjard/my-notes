package com.my.notes.users.services;

import com.my.notes.users.UserBean;
import com.my.notes.users.UserBeanUpdateRequest;
import com.my.notes.users.exceptions.UserException;
import com.my.notes.users.mappers.UserMapper;
import com.my.notes.users.model.User;
import com.my.notes.users.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersServices {

    private UsersRepository usersRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UsersServices(UsersRepository usersRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserBean getUserByEmail(String email) throws UserException {
        return userMapper.convertToUserBean(
                usersRepository.findByEmail(email).orElseThrow(
                        () -> new UserException("User not found")
                )
        );
    }

    public List<UserBean> getUsers(int userId) throws UserException {
        List<UserBean> userBeans = new ArrayList<>();
        if (userId == 0) {
            // no userId provided => return all users
            usersRepository.findAll().forEach(user -> {
                user.setPassword("");
                userBeans.add(
                        userMapper.convertToUserBean(user)
                );
            });
        } else {
            // userId provided => search this user
            User user = usersRepository.findById(userId).orElseThrow(() -> new UserException("Pas d'utilisateur avec l'id "+userId));
            user.setPassword("");
            userBeans.add(
                    userMapper.convertToUserBean(user)
            );
        }

        return userBeans;
    }

    public void createUser(UserBean userBean) {
        userBean.setPassword(
                passwordEncoder.encode(
                        userBean.getPassword()
                )
        );
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
        userBean.setPassword(
                passwordEncoder.encode(
                        userBean.getPassword()
                )
        );
        user.setName(userBean.getName());
        user.setSurname(userBean.getSurname());
        usersRepository.saveAndFlush(user);
    }

}
