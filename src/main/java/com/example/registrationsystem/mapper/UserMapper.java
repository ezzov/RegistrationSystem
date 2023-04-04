package com.example.registrationsystem.mapper;

import com.example.registrationsystem.dto.UserDto;
import com.example.registrationsystem.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "roles", source = "roles")
    UserDto toUserDto(User user);

    List<UserDto> toListUserDto (List<User> users);
}
