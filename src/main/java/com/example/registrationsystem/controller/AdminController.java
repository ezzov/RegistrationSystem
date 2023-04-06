package com.example.registrationsystem.controller;

import com.example.registrationsystem.dto.UserDto;
import com.example.registrationsystem.mapper.UserMapper;
import com.example.registrationsystem.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller that handles requests of users with role ADMIN
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
public class AdminController {

    private UserService userService;
    private UserMapper userMapper;

    /**
     * End-point that finds all users
     * @return {@link List} of {@link UserDto} with user data
     */
    @GetMapping("/all-users")
    public List<UserDto> findAllUsers(){
        log.info("Return list of all users");
        return userMapper.toListUserDto(userService.findAllUsers());
    }

    /**
     * End-point that finds user by username or part of username
     * @param userName from request query parameters
     * @return {@link UserDto} with user data
     */
    @GetMapping("/user")
    public UserDto findUserByUserName(String userName) {
        log.info("Return user with userName: {}", userName);
        return userMapper.toUserDto(userService.findUserByUserName(userName));
    }

    /**
     * End-point that assigns operator rights to user
     * @param userId User id from request query parameters
     * @return {@link UserDto} with user data
     */
    @PatchMapping("/set-role")
    public UserDto setOperatorRole(@RequestParam long userId) {
        log.info("Return user with id: {}", userId);
        return userMapper.toUserDto(userService.setOperatorRole(userId));
    }
}
