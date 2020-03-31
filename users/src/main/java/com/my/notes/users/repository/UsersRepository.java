package com.my.notes.users.repository;

import com.my.notes.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Integer> {
}
