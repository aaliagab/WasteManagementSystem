package com.microservice.wastemanageraddressservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WasteCenterAuthorizationDto {
    private Long id;
    private String authorizationNumber;
    private Long wasteManagerId;
}
