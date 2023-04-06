package com.example.registrationsystem.repository;

import com.example.registrationsystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository that stores {@link User} entities
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Method that finds all users by username or a part of username
     * @param userName from {@link User}
     * @return {@link List} of {@link User}
     */
    List<User> findAllByUserNameContaining(String userName);

    /**
     * Method that finds user by email
     * @param email from {@link User}
     * @return {@link Optional} wrap of {@link User}
     */
    Optional<User> findUserByEmail(String email);

}
