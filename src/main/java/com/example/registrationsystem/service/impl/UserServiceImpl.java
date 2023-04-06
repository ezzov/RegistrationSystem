package com.example.registrationsystem.service.impl;

import com.example.registrationsystem.models.Role;
import com.example.registrationsystem.models.User;
import com.example.registrationsystem.repository.RoleRepository;
import com.example.registrationsystem.repository.UserRepository;
import com.example.registrationsystem.service.UserService;
import com.example.registrationsystem.util.exception.AssignRoleRightsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Implementation of {@link UserService}
 * Works with {@link RoleRepository} and {@link UserRepository}.
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    /**
     * {@inheritDoc}
     * Uses method {@link UserRepository#findAll}
     * @return {@link List} of {@link User}
     */
    @Override
    public List<User> findAllUsers() {
        log.info("Request for all users from database");
        List<User> users = userRepository.findAll();
        log.info("Return list of users");
        return users;
    }

    /**
     * {@inheritDoc}
     * Uses method {@link UserRepository#findAllByUserNameContaining}
     * @param userName from {@link User}
     * @return {@link User}
     */
    @Override
    //в данном методе я сделала как в задание, но на мой взгляд лучше возвращать List<User>, так как поле userName у нас не уникально
    public User findUserByUserName(String userName) {
        log.info("Request for all users by username from database");
        List<User> users = userRepository.findAllByUserNameContaining(userName);
        Optional<User> user = users.stream().findFirst();
        if (user.isEmpty()) {
            log.error("User Not found");
            throw new EntityNotFoundException("User Not found");
        }
        log.info("Return user");
        return user.get();
    }

    /**
     * {@inheritDoc}
     * Uses method {@link UserRepository#save}
     * @param userId from {@link User}
     * @return {@link User}
     */
    @Override
    @Transactional
    public User setOperatorRole(long userId) {
        log.info("Request for user with id: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found by id = " + userId));
        log.info("Request for role OPERATOR with id=2 from data base");
        Role roleOperator = roleRepository.findById(2)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
        Set<Role> roles = user.getRoles();
        if (roles.isEmpty() || roles.contains(roleOperator) ||
                roles.stream().map(Role::getRole).noneMatch(role -> role.equals("USER"))) {
            log.error("User has already role operator or has not role user");
            throw new AssignRoleRightsException("Can not be OPERATOR");
        }
        roles.add(roleOperator);
        log.info("Return user");
        return userRepository.save(user);
    }
}
