package com.example.registrationsystem.repository;

import com.example.registrationsystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByUserNameContaining(String userName);

    Optional<User> findUserByEmail(String email);

}
