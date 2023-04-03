package com.example.registrationsystem.mapper;

import com.example.registrationsystem.dto.EditRequestDto;
import com.example.registrationsystem.dto.RequestDto;
import com.example.registrationsystem.dto.RequestDtoWithPagination;
import com.example.registrationsystem.models.Request;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RequestMapper {

    @Mapping(target = "user.id", source = "userId")
    Request requestDtoToRequest(RequestDto requestDto);

    Request editRequestDtoToRequest(EditRequestDto editRequestDto);

    @Mapping(target = "currentPage", source = "pageable.pageNumber")
    @Mapping(target = "totalPages", source = "totalPages")
    @Mapping(target = "requests", source = "content")
    RequestDtoWithPagination toRequestDtoWithPagination(Page<Request> requestPage);
}
