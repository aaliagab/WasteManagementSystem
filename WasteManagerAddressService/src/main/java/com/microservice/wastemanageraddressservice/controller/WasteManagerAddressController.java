package com.microservice.wastemanageraddressservice.controller;

import com.microservice.wastemanageraddressservice.dto.WasteManagerAddressDto;
import com.microservice.wastemanageraddressservice.dto.mapper.request.WasteManagerAddressRequest;
import com.microservice.wastemanageraddressservice.exception.WasteManagerAddressNotFoundException;
import com.microservice.wastemanageraddressservice.exception.WasteManagerNotFoundException;
import com.microservice.wastemanageraddressservice.service.WasteManagerAddressService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/waste-manager-addresses")
public class WasteManagerAddressController {

    private final WasteManagerAddressService service;

    public WasteManagerAddressController(WasteManagerAddressService service) {
        this.service = service;
    }


    @PostMapping
    @Operation(summary = "Create a new Waste Manager Address", description = "Creates a new Waste Manager Address and returns it. It is not necessary to send the Address ID but WasteManager ID is required")
    public ResponseEntity<?> createAddress(@Validated @RequestBody WasteManagerAddressDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        try {
            WasteManagerAddressDto createdDto = service.createAddress(dto);
            return ResponseEntity.ok(createdDto);
        }catch (WasteManagerNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Error while creating waste manager: " + e.getMessage());
        }
    }

    @GetMapping
    @Operation(summary = "Get all Waste Manager Addresses", description = "Retrieves and returns all Waste Manager Addresses.")
    public ResponseEntity<List<WasteManagerAddressDto>> getAllAddresses() {
        List<WasteManagerAddressDto> dtos = service.getAllAddresses();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/by-waste-manager/{id}")
    @Operation(summary = "Get a Waste Manager Address by Waste Manager ID", description = "Retrieves and returns a Waste Manager Address by Waste Manager ID.")
    public ResponseEntity<?> getAddressByWasteManagerId(@PathVariable Long id) {
        try{
            WasteManagerAddressDto dto = service.getAddressByWasteManagerId(id);
            return ResponseEntity.ok(dto);
        }catch (WasteManagerNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Waste Manager Address by ID", description = "Retrieves a Waste Manager Address by its ID.")
    public ResponseEntity<?> getAddressById(@PathVariable Long id) {
        try {
            WasteManagerAddressDto addressDto = service.getAddressById(id);
            return ResponseEntity.ok(addressDto);
        } catch (WasteManagerAddressNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error while retrieving waste manager address: " + e.getMessage());
        }
    }


    @PutMapping()
    @Operation(summary = "Update a Waste Manager Address", description = "Updates a Waste Manager Address by its ID.")
    public ResponseEntity<?> updateAddress(@Validated @RequestBody WasteManagerAddressRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        try {
            WasteManagerAddressDto updatedDto = service.updateAddress(request);
            return ResponseEntity.ok(updatedDto);
        } catch (WasteManagerAddressNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error while updating waste manager address: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Waste Manager Address", description = "Deletes a Waste Manager Address by its ID.")
    public ResponseEntity<?> deleteAddress(@PathVariable Long id) {
        try {
            service.deleteAddress(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (WasteManagerAddressNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while deleting waste manager address: " + e.getMessage());
        }
    }
    @DeleteMapping("/by-waste-manager/{id}")
    @Operation(summary = "Delete a Waste Manager Address by Waste Manager ID", description = "Deletes a Waste Manager Address by Waste Manager ID.")
    public ResponseEntity<?> deleteAddressByWasteManagerId(@PathVariable Long id) {
        try {
            service.deleteAddressByWasteManagerId(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (WasteManagerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while deleting waste manager address: " + e.getMessage());
        }
    }
}
