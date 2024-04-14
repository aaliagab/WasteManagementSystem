package com.microservice.wastemanagerservice.dto.mapper;

import com.microservice.wastemanagerservice.client.AddressServiceClient;
import com.microservice.wastemanagerservice.dto.WasteCenterAuthorizationDto;
import com.microservice.wastemanagerservice.dto.WasteManagerAddressDto;
import com.microservice.wastemanagerservice.dto.WasteManagerDto;
import com.microservice.wastemanagerservice.dto.response.WasteManagerResponse;
import com.microservice.wastemanagerservice.model.WasteManager;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class WasteManagerMapper implements IMapper<WasteManager,WasteManagerDto>{
    private final WasteCenterAuthorizationMapper authorizationMapper;
    private final AddressServiceClient addressServiceClient;

    public WasteManagerMapper(WasteCenterAuthorizationMapper authorizationMapper, AddressServiceClient addressServiceClient) {
        this.authorizationMapper = authorizationMapper;
        this.addressServiceClient = addressServiceClient;
    }

    @Override
    public WasteManagerDto mapToDto(WasteManager m) {
        List<WasteCenterAuthorizationDto> wasteCenterAuthorizationDtoList = m.getAuthorizations().stream()
                .map(authorizationMapper::mapToDto)
                .collect(Collectors.toList());

        WasteManagerDto dto = WasteManagerDto.builder()
                .name(m.getName())
                .nif(m.getNif())
                .isEnabled(m.getIsEnabled())
                .authorizations(wasteCenterAuthorizationDtoList)
                .build();
        return dto;
    }

    @Override
    public List<WasteManagerDto> mapToDtoList(List<WasteManager> list) {
        return list.stream().map(obj->mapToDto(obj)).collect(Collectors.toList());
    }

    public WasteManagerResponse mapToResponse(WasteManager m) throws ServiceException{
        List<WasteCenterAuthorizationDto> wasteCenterAuthorizationDtoList = m.getAuthorizations().stream()
                .map(authorizationMapper::mapToDto)
                .collect(Collectors.toList());

        ResponseEntity<WasteManagerAddressDto> responseEntity = addressServiceClient.getAddressByWasteManagerId(m.getId());
        WasteManagerAddressDto addressDto = null;

        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() instanceof WasteManagerAddressDto) {
            addressDto = (WasteManagerAddressDto) responseEntity.getBody();
        } else if (responseEntity.getStatusCode().is2xxSuccessful() || responseEntity.getStatusCode() == HttpStatus.NOT_FOUND) {
            //addressDto = null;
            return WasteManagerResponse.builder()
                    .name(m.getName())
                    .nif(m.getNif())
                    .isEnabled(m.getIsEnabled())
                    .authorizations(wasteCenterAuthorizationDtoList)
                    .addressDto(addressDto)
                    .build();
        } else {
            throw new ServiceException("Unexpected response status: " + responseEntity.getStatusCode() + " from Address Service");
        }

        return WasteManagerResponse.builder()
                .name(m.getName())
                .nif(m.getNif())
                .isEnabled(m.getIsEnabled())
                .authorizations(wasteCenterAuthorizationDtoList)
                .addressDto(addressDto)
                .build();
    }

    public List<WasteManagerResponse> mapToResponseList(List<WasteManager> list) {
        return list.stream().map(obj->mapToResponse(obj)).collect(Collectors.toList());
    }
}