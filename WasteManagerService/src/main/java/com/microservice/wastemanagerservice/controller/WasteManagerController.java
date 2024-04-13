package com.microservice.wastemanagerservice.controller;

import com.microservice.wastemanagerservice.dto.WasteManagerDto;
import com.microservice.wastemanagerservice.service.WasteManagerService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/waste-managers")
public class WasteManagerController {

    @Autowired
    private WasteManagerService wasteManagerService;

    @PostMapping
    @Operation(summary = "Create a new WasteManager", description = "Creates a new WasteManager and returns it.")
    public ResponseEntity<?> createWasteManager(@Validated @RequestBody WasteManagerDto wasteManagerDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        try {
            WasteManagerDto created = wasteManagerService.createWasteManager(wasteManagerDto);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error while creating waste manager: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a WasteManager", description = "Update a WasteManager and returns it.")
    public ResponseEntity<?> updateWasteManager(@PathVariable Long id, @Validated @RequestBody WasteManagerDto wasteManagerDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        try {
            WasteManagerDto updated = wasteManagerService.updateWasteManager(id, wasteManagerDto);
            return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error while updating waste manager: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a WasteManager", description = "Find a WasteManager by id and returns it.")
    public ResponseEntity<?> getWasteManagerById(@PathVariable Long id) {
        try {
            WasteManagerDto wasteManager = wasteManagerService.findById(id);
            return wasteManager != null ? ResponseEntity.ok(wasteManager) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving waste manager: " + e.getMessage());
        }
    }

    @GetMapping
    @Operation(summary = "Get all WasteManagers", description = "Get all WasteManagers from DB.")
    public ResponseEntity<?> getAllWasteManagers() {
        try {
            List<WasteManagerDto> wasteManagers = wasteManagerService.findAll();
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
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error deleting waste manager: " + e.getMessage());
        }
    }
}