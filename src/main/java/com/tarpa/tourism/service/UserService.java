package com.tarpa.tourism.service;

import com.tarpa.tourism.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User saveUser(User user);

    User updateUser(Long id, User user);

    List<User> getAllUsers();

    Optional<User> getUserById(Long id);

    Optional<User> getUserByEmail(String email);

    void deleteUser(Long id);

    boolean existsByEmail(String email);

}