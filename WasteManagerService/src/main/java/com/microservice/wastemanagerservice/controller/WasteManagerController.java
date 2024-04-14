package com.microservice.wastemanagerservice.controller;

import com.microservice.wastemanagerservice.dto.WasteManagerDto;
import com.microservice.wastemanagerservice.dto.request.WasteManagerRequest;
import com.microservice.wastemanagerservice.dto.response.WasteManagerResponse;
import com.microservice.wastemanagerservice.exceptions.WasteManagerNotFoundException;
import com.microservice.wastemanagerservice.service.WasteManagerService;
import io.swagger.v3.oas.annotations.Operation;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/waste-managers")
public class WasteManagerController {

    private final WasteManagerService wasteManagerService;

    public WasteManagerController(WasteManagerService wasteManagerService) {
        this.wasteManagerService = wasteManagerService;
    }

    @PostMapping
    @Operation(summary = "Create a new WasteManager", description = "Creates a new WasteManager and returns it. It is not necessary to send the ID and WasteManager ID in the authorizations")
    public ResponseEntity<?> createWasteManager(@Validated @RequestBody WasteManagerDto wasteManagerRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        try {
            WasteManagerDto created = wasteManagerService.createWasteManager(wasteManagerRequest);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error while creating waste manager: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a WasteManager", description = "Update a WasteManager and returns it.")
    public ResponseEntity<?> updateWasteManager(@PathVariable Long id, @Validated @RequestBody WasteManagerRequest wasteManagerRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        try {
            WasteManagerResponse updated = wasteManagerService.updateWasteManager(id, wasteManagerRequest);
            return ResponseEntity.ok(updated);
        } catch (WasteManagerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a WasteManager", description = "Find a WasteManager by id and returns it.")
    public ResponseEntity<?> getWasteManagerById(@PathVariable Long id) {
        try {
            WasteManagerResponse wasteManager = wasteManagerService.findById(id);
            return ResponseEntity.ok(wasteManager);
        } catch (WasteManagerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(e.getMessage());
        }
    }

    @GetMapping
    @Operation(summary = "Get all WasteManagers", description = "Get all WasteManagers from DB.")
    public ResponseEntity<?> getAllWasteManagers() {
        try {
            List<WasteManagerResponse> wasteManagers = wasteManagerService.findAll();
            return ResponseEntity.ok(wasteManagers);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving all waste managers: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete WasteManager", description = "Delete a WasteManager by id.")
    public ResponseEntity<?> deleteWasteManager(@PathVariable Long id) {
        try {
            wasteManagerService.deleteWasteManager(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (WasteManagerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
