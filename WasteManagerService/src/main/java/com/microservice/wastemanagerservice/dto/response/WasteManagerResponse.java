package com.microservice.wastemanagerservice.dto.response;

import com.microservice.wastemanagerservice.dto.WasteCenterAuthorizationDto;
import com.microservice.wastemanagerservice.dto.WasteManagerAddressDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class WasteManagerResponse {
    private Long id;
    private String name;
    private String nif;
    private List<WasteCenterAuthorizationDto> authorizations;
    private Boolean isEnabled;
    private WasteManagerAddressDto addressDto;
}
