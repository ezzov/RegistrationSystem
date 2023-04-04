package com.example.registrationsystem.service.impl;

import com.example.registrationsystem.models.Role;
import com.example.registrationsystem.models.User;
import com.example.registrationsystem.repository.RoleRepository;
import com.example.registrationsystem.repository.UserRepository;
import com.example.registrationsystem.service.UserService;
import com.example.registrationsystem.util.exception.AssignRoleRightsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<User> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

    @Override
    //в данном методе я сделала как в задание, но на мой взгляд лучше возвращать List<User>, так как поле userName у нас не уникально
    public User findUserByUserName(String userName) {
        List<User> users = userRepository.findAllByUserNameContaining(userName);
        Optional<User> user = users.stream().findFirst();
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User Not found");
        }
        return user.get();
    }

    @Override
    @Transactional
    public User setOperatorRole(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found by id = " + userId));
        Role roleOperator = roleRepository.findById(2)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
        Set<Role> roles = user.getRoles();
        if (roles.isEmpty() || roles.contains(roleOperator) ||
                roles.stream().map(Role::getRole).noneMatch(role -> role.equals("USER"))) {
            throw new AssignRoleRightsException("Can not be OPERATOR");
        }
        roles.add(roleOperator);
        return userRepository.save(user);
    }
}
