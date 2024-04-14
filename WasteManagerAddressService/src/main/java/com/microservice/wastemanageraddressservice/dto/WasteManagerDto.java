package com.microservice.wastemanageraddressservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WasteManagerDto {
    private String name;
    private String nif;
    private List<WasteCenterAuthorizationDto> authorizations;
    private Boolean isEnabled;
}
