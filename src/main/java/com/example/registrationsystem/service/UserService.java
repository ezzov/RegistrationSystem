package com.example.registrationsystem.service;

import com.example.registrationsystem.models.User;

import java.util.List;

/**
 * Service that works with {@link User}
 */
public interface UserService {

    /**
     * Method that finds all users
     * @return {@link List} of {@link User}
     */
    List<User> findAllUsers();

    /**
     * Method that find user by username or a part of username
     * @param userName from {@link User}
     * @return {@link User}
     */
    User findUserByUserName(String userName);

    /**
     * Method that add operator role to user
     * @param userId from {@link User}
     * @return {@link User}
     */
    User setOperatorRole(long userId);
}
