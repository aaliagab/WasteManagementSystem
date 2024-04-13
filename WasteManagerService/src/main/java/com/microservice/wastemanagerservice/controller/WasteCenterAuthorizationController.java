package com.microservice.wastemanagerservice.controller;

import com.microservice.wastemanagerservice.dto.WasteCenterAuthorizationDto;
import com.microservice.wastemanagerservice.dto.request.WasteCenterAuthorizationRequest;
import com.microservice.wastemanagerservice.exceptions.WasteManagerNotFoundException;
import com.microservice.wastemanagerservice.service.WasteCenterAuthorizationService;
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
    public ResponseEntity<?> createAuthorization(@RequestBody WasteCenterAuthorizationRequest authorizationRequest) {
        try{
            WasteCenterAuthorizationDto created = authorizationService.createAuthorization(authorizationRequest);
            return ResponseEntity.ok(created);
        }catch (WasteManagerNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<WasteCenterAuthorizationDto> updateAuthorization(@PathVariable Long id, @RequestBody WasteCenterAuthorizationDto authorizationDto) {
        WasteCenterAuthorizationDto updated = authorizationService.updateAuthorization(id, authorizationDto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthorization(@PathVariable Long id) {
        authorizationService.deleteAuthorization(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WasteCenterAuthorizationDto> getAuthorizationById(@PathVariable Long id) {
        WasteCenterAuthorizationDto authorization = authorizationService.findAuthorizationById(id);
        return authorization != null ? ResponseEntity.ok(authorization) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<WasteCenterAuthorizationDto>> getAllAuthorizations() {
        List<WasteCenterAuthorizationDto> authorizations = authorizationService.findAllAuthorizations();
        return ResponseEntity.ok(authorizations);
    }
}
