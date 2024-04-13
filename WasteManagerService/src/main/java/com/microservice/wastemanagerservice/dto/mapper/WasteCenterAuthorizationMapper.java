package com.microservice.wastemanagerservice.dto.mapper;

import com.microservice.wastemanagerservice.dto.WasteCenterAuthorizationDto;
import com.microservice.wastemanagerservice.dto.request.WasteCenterAuthorizationRequest;
import com.microservice.wastemanagerservice.model.WasteCenterAuthorization;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WasteCenterAuthorizationMapper implements IMapper<WasteCenterAuthorization, WasteCenterAuthorizationDto> {

    @Override
    public WasteCenterAuthorizationDto mapToDto(WasteCenterAuthorization m) {
        WasteCenterAuthorizationDto dto = WasteCenterAuthorizationDto.builder()
                .id(m.getId())
                .authorizationNumber(m.getAuthorizationNumber())
                .wasteManagerId(m.getWasteManager().getId())
                .build();
        return dto;
    }

    @Override
    public List<WasteCenterAuthorizationDto> mapToDtoList(List<WasteCenterAuthorization> list) {
        return list.stream().map(obj->mapToDto(obj)).collect(Collectors.toList());
    }

    public WasteCenterAuthorizationRequest mapToRequest(WasteCenterAuthorization m) {
        WasteCenterAuthorizationRequest request = WasteCenterAuthorizationRequest.builder()
                .authorizationNumber(m.getAuthorizationNumber())
                .wasteManagerId(m.getWasteManager().getId())
                .build();
        return request;
    }


}