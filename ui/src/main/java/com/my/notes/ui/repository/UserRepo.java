package com.my.notes.ui.repository;

import com.my.notes.ui.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserRepo {

    private List<User> users;

    @PostConstruct
    public void init() {
        users = new ArrayList<>();
        String[] userNames = {"John Doe", "David Danjard", "Jane Doe"};
        Arrays.asList(userNames).forEach(userName -> {
            users.add(
                    getUser(users.size(), userName)
            );
        });
    }

    public List<User> getUsers() {
        return users;
    }

    private static User getUser(int id, String name) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        return user;
    }

}
