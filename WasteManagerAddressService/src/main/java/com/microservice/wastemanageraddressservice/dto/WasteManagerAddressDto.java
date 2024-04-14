package com.microservice.wastemanageraddressservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class WasteManagerAddressDto {
    private Long id;
    private String address;
    private Boolean isEnabled;
    private Long version;
    private Date createdDate;
    private Date lastModifiedDate;
    private Long wasteManagerId;
}
