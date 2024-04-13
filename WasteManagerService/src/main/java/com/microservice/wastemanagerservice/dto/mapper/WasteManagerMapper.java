package com.microservice.wastemanagerservice.dto.mapper;

import com.microservice.wastemanagerservice.dto.WasteManagerDto;
import com.microservice.wastemanagerservice.model.WasteManager;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface WasteManagerMapper {
    WasteManagerMapper INSTANCE = Mappers.getMapper(WasteManagerMapper.class);
    WasteManager dtoToEntity(WasteManagerDto dto);
    WasteManagerDto entityToDto(WasteManager entity);

    List<WasteManagerDto> entityToDto(List<WasteManager> entity);
}