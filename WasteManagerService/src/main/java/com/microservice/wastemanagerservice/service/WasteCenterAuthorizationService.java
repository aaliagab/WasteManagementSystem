package com.microservice.wastemanagerservice.service;

import com.microservice.wastemanagerservice.dto.WasteCenterAuthorizationDto;
import com.microservice.wastemanagerservice.dto.mapper.WasteCenterAuthorizationMapper;
import com.microservice.wastemanagerservice.dto.mapper.WasteManagerMapper;
import com.microservice.wastemanagerservice.model.WasteCenterAuthorization;
import com.microservice.wastemanagerservice.repository.WasteCenterAuthorizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WasteCenterAuthorizationService {

    @Autowired
    private WasteCenterAuthorizationRepository repository;

    // Create a new authorization
    public WasteCenterAuthorizationDto createAuthorization(WasteCenterAuthorizationDto dto) {
        WasteCenterAuthorization authorization = WasteCenterAuthorizationMapper.INSTANCE.dtoToEntity(dto);
        return WasteCenterAuthorizationMapper.INSTANCE.entityToDto(repository.save(authorization));
    }

    // Update an existing authorization
    public WasteCenterAuthorizationDto updateAuthorization(Long id, WasteCenterAuthorizationDto dto) {
        return repository.findById(id)
                .map(existingAuthorization -> {
                    existingAuthorization.setAuthorizationNumber(dto.getAuthorizationNumber());
                    return WasteCenterAuthorizationMapper.INSTANCE.entityToDto(repository.save(existingAuthorization));
                }).orElse(null);
    }

    // Find an authorization by ID
    public WasteCenterAuthorizationDto findAuthorizationById(Long id) {
        return repository.findById(id)
                .map(WasteCenterAuthorizationMapper.INSTANCE::entityToDto)
                .orElse(null);
    }

    // Delete an authorization
    public void deleteAuthorization(Long id) {
        repository.deleteById(id);
    }

    // List all authorizations
    public List<WasteCenterAuthorizationDto> findAllAuthorizations() {
        return WasteCenterAuthorizationMapper.INSTANCE.entityToDto(repository.findAll());
    }
}
