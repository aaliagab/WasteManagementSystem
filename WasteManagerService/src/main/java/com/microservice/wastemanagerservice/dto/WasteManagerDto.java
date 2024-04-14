package com.microservice.wastemanagerservice.dto;

import com.microservice.wastemanagerservice.dto.request.WasteCenterAuthorizationRequest;
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
