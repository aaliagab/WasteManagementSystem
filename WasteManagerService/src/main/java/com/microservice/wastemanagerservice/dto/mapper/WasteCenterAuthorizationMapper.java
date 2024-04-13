package com.microservice.wastemanagerservice.dto.mapper;

import com.microservice.wastemanagerservice.dto.WasteCenterAuthorizationDto;
import com.microservice.wastemanagerservice.model.WasteCenterAuthorization;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface WasteCenterAuthorizationMapper {
    WasteCenterAuthorizationMapper INSTANCE = Mappers.getMapper(WasteCenterAuthorizationMapper.class);

    WasteCenterAuthorizationDto entityToDto(WasteCenterAuthorization authorization);

    WasteCenterAuthorization dtoToEntity(WasteCenterAuthorizationDto dto);

    List<WasteCenterAuthorization> dtoToEntity(List<WasteCenterAuthorizationDto> dtos);
    List<WasteCenterAuthorizationDto> entityToDto(List<WasteCenterAuthorization> entities);


}