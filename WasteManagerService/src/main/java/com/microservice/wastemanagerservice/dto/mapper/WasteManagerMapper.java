package com.microservice.wastemanagerservice.dto.mapper;

import com.microservice.wastemanagerservice.dto.WasteCenterAuthorizationDto;
import com.microservice.wastemanagerservice.dto.WasteManagerDto;
import com.microservice.wastemanagerservice.dto.request.WasteCenterAuthorizationRequest;
import com.microservice.wastemanagerservice.model.WasteCenterAuthorization;
import com.microservice.wastemanagerservice.model.WasteManager;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class WasteManagerMapper implements IMapper<WasteManager,WasteManagerDto>{
    private final WasteCenterAuthorizationMapper authorizationMapper;

    public WasteManagerMapper(WasteCenterAuthorizationMapper authorizationMapper) {
        this.authorizationMapper = authorizationMapper;
    }

    @Override
    public WasteManagerDto mapToDto(WasteManager m) {
        List<WasteCenterAuthorizationRequest> wasteCenterAuthorizationRequestList = m.getAuthorizations().stream()
                .map(authorizationMapper::mapToRequest)
                .collect(Collectors.toList());

        WasteManagerDto dto = WasteManagerDto.builder()
                .name(m.getName())
                .nif(m.getNif())
                .isEnabled(m.getIsEnabled())
                .authorizations(wasteCenterAuthorizationRequestList)
                .build();
        return dto;
    }

    @Override
    public List<WasteManagerDto> mapToDtoList(List<WasteManager> list) {
        return list.stream().map(obj->mapToDto(obj)).collect(Collectors.toList());
    }
}