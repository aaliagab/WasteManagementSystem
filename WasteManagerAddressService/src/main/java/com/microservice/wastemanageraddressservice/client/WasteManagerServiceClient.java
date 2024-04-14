package com.microservice.wastemanageraddressservice.client;

import com.microservice.wastemanageraddressservice.dto.WasteManagerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "wasteManagerService", url = "${waste.manager.service.url}")
@Component
public interface WasteManagerServiceClient {
    @GetMapping("/{id}")
    ResponseEntity<WasteManagerDto> getWasteManagerById(@PathVariable("id") Long id);
}

