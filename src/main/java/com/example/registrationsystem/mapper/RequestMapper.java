package com.example.registrationsystem.mapper;

import com.example.registrationsystem.dto.EditRequestDto;
import com.example.registrationsystem.dto.RequestDto;
import com.example.registrationsystem.dto.RequestDtoForResponse;
import com.example.registrationsystem.dto.RequestDtoWithPagination;
import com.example.registrationsystem.models.Request;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

/**
 * Mapper that converts entity {@link Request} to data transfer object and vice versa.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RequestMapper {

    /**
     * Method that converts request dto to request
     * @param requestDto {@link RequestDto}
     * @return {@link Request}
     */
    @Mapping(target = "user.id", source = "userId")
    Request requestDtoToRequest(RequestDto requestDto);

    /**
     * Method that converts edit request dto to request
     * @param editRequestDto {@link EditRequestDto}
     * @return {@link Request}
     */
    Request editRequestDtoToRequest(EditRequestDto editRequestDto);

    /**
     * Method that converts page of requests to request dto with pagination
     * @param requestPage {@link Page} of {@link Request}
     * @return {@link RequestDtoWithPagination}
     */
    @Mapping(target = "currentPage", source = "pageable.pageNumber")
    @Mapping(target = "totalPages", source = "totalPages")
    @Mapping(target = "requests", source = "content")
    RequestDtoWithPagination toRequestDtoWithPagination(Page<Request> requestPage);

    /**
     * Method that converts request to request dto for response
     * @param request {@link Request}
     * @return {@link RequestDtoForResponse}
     */
    @Mapping(target = "userName", source = "user.userName")
    RequestDtoForResponse requestToRequestDtoForResponse(Request request);
}
