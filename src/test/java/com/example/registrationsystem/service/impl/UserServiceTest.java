package com.example.registrationsystem.service.impl;

import com.example.registrationsystem.models.Role;
import com.example.registrationsystem.models.User;
import com.example.registrationsystem.repository.RoleRepository;
import com.example.registrationsystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    private Role roleUser;
    private User user1;
    private User user2;
    List<User> users;

    @BeforeEach
    void setup() {
        roleUser = Role.builder()
                .id(1)
                .role("USER")
                .build();
        user1 = User.builder()
                .id(1L)
                .userName("Lady")
                .password("Lady")
                .email("lady@gmail.com")
                .roles(Set.of(roleUser))
                .build();
        user2 = User.builder()
                .id(2L)
                .userName("Gaga")
                .password("Gaga")
                .email("gaga@gmail.com")
                .roles(Set.of(roleUser))
                .build();
        users = Arrays.asList(user1, user2);
    }

    @Test
    void findAllUsers_shouldReturnListOfUsers() {
        when(userRepository.findAll()).thenReturn(users);
        List<User> resultUsers = userService.findAllUsers();
        assertEquals(2, resultUsers.size());
        assertEquals("Gaga", resultUsers.get(1).getUserName());
    }

    @Test
    void findUserByUserName_shouldReturnUser() {
        users = List.of(user1);
        when(userRepository.findAllByUserNameContaining("Lady")).thenReturn(users);
        User resultUser = userService.findUserByUserName("Lady");
        assertEquals(1l, resultUser.getId());
    }
}