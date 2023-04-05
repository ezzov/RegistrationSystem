package com.example.registrationsystem.mapper;

import com.example.registrationsystem.dto.AccessAndRefreshTokensDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthenticationMapper {

    AccessAndRefreshTokensDto toAccessAndRefreshTokensDto(String accessToken, String refreshToken);
}
