package com.microservice.wastemanagerservice.controller;

import com.microservice.wastemanagerservice.dto.WasteCenterAuthorizationDto;
import com.microservice.wastemanagerservice.dto.request.WasteCenterAuthorizationRequest;
import com.microservice.wastemanagerservice.exceptions.WasteCenterAuthorizationNotFoundException;
import com.microservice.wastemanagerservice.exceptions.WasteManagerNotFoundException;
import com.microservice.wastemanagerservice.service.WasteCenterAuthorizationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/waste-center-authorizations")
public class WasteCenterAuthorizationController {

    @Autowired
    private WasteCenterAuthorizationService authorizationService;

    @PostMapping
    @Operation(summary = "Create a Waste Center Authorization", description = "Create a Waste Center Authorization by id and returns it.")
    public ResponseEntity<?> createAuthorization(@RequestBody WasteCenterAuthorizationRequest authorizationRequest) {
        try{
            WasteCenterAuthorizationDto created = authorizationService.createAuthorization(authorizationRequest);
            return ResponseEntity.ok(created);
        }catch (WasteManagerNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Waste Center Authorization", description = "Update a Waste Center Authorization by id and returns it. It is not necessary to send the WasteManager ID in the authorizations")
    public ResponseEntity<?> updateAuthorization(@RequestBody WasteCenterAuthorizationDto authorizationDto) {
        try{
            WasteCenterAuthorizationDto updated = authorizationService.updateAuthorization(authorizationDto);
            return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
        }catch (WasteCenterAuthorizationNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Waste Center Authorization", description = "Delete a Waste Center Authorization by id.")
    public ResponseEntity<?> deleteAuthorization(@PathVariable Long id) {
        try{
            authorizationService.deleteAuthorization(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (WasteCenterAuthorizationNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Waste Center Authorization", description = "Find a Waste Center Authorization by id and returns it.")
    public ResponseEntity<?> getAuthorizationById(@PathVariable Long id) {
        try{
            WasteCenterAuthorizationDto authorization = authorizationService.findAuthorizationById(id);
            return authorization != null ? ResponseEntity.ok(authorization) : ResponseEntity.notFound().build();
        }catch (WasteCenterAuthorizationNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    @Operation(summary = "Get all Waste Center Authorizations", description = "Find all Waste Center Authorizations from Data Base.")
    public ResponseEntity<List<WasteCenterAuthorizationDto>> getAllAuthorizations() {
        List<WasteCenterAuthorizationDto> authorizations = authorizationService.findAllAuthorizations();
        return ResponseEntity.ok(authorizations);
    }
}
