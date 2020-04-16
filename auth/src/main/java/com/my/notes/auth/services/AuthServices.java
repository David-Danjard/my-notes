package com.my.notes.auth.services;

import com.my.notes.auth.UserBean;
import com.my.notes.auth.UserPasswordToken;
import com.my.notes.auth.exceptions.UnauthorizedException;
import com.my.notes.auth.mappers.UserMapper;
import com.my.notes.auth.model.User;
import com.my.notes.auth.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServices {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServices.class);

    private final UsersRepository usersRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServices(UsersRepository usersRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserBean authenticateUser(UserPasswordToken userPasswordToken) throws UnauthorizedException {
        User user = usersRepository.findByEmail(userPasswordToken.getIdentifier())
                .orElseThrow(() -> new UnauthorizedException("Invalid identifier"));
        if (!passwordEncoder.matches(userPasswordToken.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid password");
        }
        LOGGER.info("User roles {} {}", user.getRole().get(0).getRole(), user.getRole());
        UserBean userBean = userMapper.convertToUserBean(user);
        LOGGER.info("UserBean roles {}", userBean.getRole());
        return userBean;
    }

}
