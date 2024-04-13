package com.microservice.wastemanagerservice.dto.request;

import com.microservice.wastemanagerservice.model.WasteManager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WasteCenterAuthorizationRequest {
    private String authorizationNumber;
    private Long wasteManagerId;
}
