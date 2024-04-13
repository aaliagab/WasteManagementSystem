package com.microservice.wastemanagerservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class WasteManagerDto {
    private Long id;
    private String name;
    private String nif;
    private List<WasteCenterAuthorizationDto> authorizations;
    private Boolean isEnabled;
}
