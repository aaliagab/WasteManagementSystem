package com.microservice.wastemanagerservice.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WasteManagerRequest {
    private String name;
    private String nif;
    private Boolean isEnabled;
}
