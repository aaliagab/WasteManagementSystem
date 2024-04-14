package com.microservice.wastemanageraddressservice.dto.mapper.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class WasteManagerAddressRequest {
    private Long id;
    private String address;
    private Boolean isEnabled;
    private Long version;
}
