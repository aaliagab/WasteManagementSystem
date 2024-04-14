package com.microservice.wastemanagerservice.client;

import com.microservice.wastemanagerservice.dto.WasteManagerAddressDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "addressService", url = "${address.service.url}")
@Component
public interface AddressServiceClient {
    @GetMapping("/by-waste-manager/{id}")
    ResponseEntity<WasteManagerAddressDto> getAddressByWasteManagerId(@PathVariable Long id);
    @DeleteMapping("/by-waste-manager/{id}")
    ResponseEntity<?> deleteAddressByWasteManagerId(@PathVariable Long id);
}

