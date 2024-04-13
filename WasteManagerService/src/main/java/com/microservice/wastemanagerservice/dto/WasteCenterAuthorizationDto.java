package com.microservice.wastemanagerservice.dto;

import lombok.Data;

@Data
public class WasteCenterAuthorizationDto {
    private Long id;
    private String authorizationNumber;
}
