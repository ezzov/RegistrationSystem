package com.example.registrationsystem.mapper;

import com.example.registrationsystem.dto.UserDto;
import com.example.registrationsystem.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

/**
 * Mapper that converts entity {@link User} to data transfer object and vice versa.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    /**
     * Method that converts user to user dto
     * @param user {@link User}
     * @return {@link UserDto}
     */
    @Mapping(target = "roles", source = "roles")
    UserDto toUserDto(User user);

    /**
     * Method that converts list of users to list of user dto
     * @param users {@link List} of {@link User}
     * @return {@link List} of {@link UserDto}
     */
    List<UserDto> toListUserDto (List<User> users);
}
