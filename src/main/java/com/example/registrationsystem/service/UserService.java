package com.example.registrationsystem.service;

import com.example.registrationsystem.models.User;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();

    User findUserByUserName(String userName);

    User setOperatorRole(long userId);
}
