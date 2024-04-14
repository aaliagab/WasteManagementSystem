package com.microservice.wastemanageraddressservice.dto.mapper;

import com.microservice.wastemanageraddressservice.dto.WasteManagerAddressDto;
import com.microservice.wastemanageraddressservice.model.WasteManagerAddressEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WasteManagerAddressMapper implements IMapper<WasteManagerAddressEntity, WasteManagerAddressDto>{

    @Override
    public WasteManagerAddressDto mapToDto(WasteManagerAddressEntity m) {
        WasteManagerAddressDto dto = WasteManagerAddressDto.builder()
                .id(m.getId())
                .address(m.getAddress())
                .wasteManagerId(m.getWasteManagerId())
                .createdDate(m.getCreatedDate())
                .lastModifiedDate(m.getLastModifiedDate())
                .version(m.getVersion())
                .isEnabled(m.getIsEnabled())
                .build();
        return dto;
    }

    @Override
    public List<WasteManagerAddressDto> mapToDtoList(List<WasteManagerAddressEntity> list) {
        return list.stream().map(obj->mapToDto(obj)).collect(Collectors.toList());
    }
}