package com.example.registrationsystem.mapper;

import com.example.registrationsystem.dto.AccessAndRefreshTokensDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * Mapper that converts tokens to data transfer object and vice versa.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthenticationMapper {

    /**
     * Method that converts access token and refreshed access token to access and refresh token dto.
     * @return {@link AccessAndRefreshTokensDto}
     */
    AccessAndRefreshTokensDto toAccessAndRefreshTokensDto(String accessToken, String refreshToken);
}
