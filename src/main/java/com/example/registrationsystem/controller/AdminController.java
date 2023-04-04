package com.example.registrationsystem.controller;

import com.example.registrationsystem.dto.UserDto;
import com.example.registrationsystem.mapper.UserMapper;
import com.example.registrationsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/all-users")
    public List<UserDto> findAllUsers(){
        return userMapper.toListUserDto(userService.findAllUsers());
    }

    @GetMapping("/user")
    public UserDto findUserByUserName(String userName) {
        return userMapper.toUserDto(userService.findUserByUserName(userName));
    }

    @PatchMapping("/set-role")
    public UserDto setOperatorRole(@RequestParam long userId) {
        return userMapper.toUserDto(userService.setOperatorRole(userId));
    }
}
