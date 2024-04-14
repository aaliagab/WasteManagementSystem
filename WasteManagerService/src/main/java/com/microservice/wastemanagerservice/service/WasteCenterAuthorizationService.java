package com.microservice.wastemanagerservice.service;

import com.microservice.wastemanagerservice.dto.WasteCenterAuthorizationDto;
import com.microservice.wastemanagerservice.dto.mapper.WasteCenterAuthorizationMapper;
import com.microservice.wastemanagerservice.dto.request.WasteCenterAuthorizationRequest;
import com.microservice.wastemanagerservice.exceptions.WasteCenterAuthorizationNotFoundException;
import com.microservice.wastemanagerservice.exceptions.WasteManagerNotFoundException;
import com.microservice.wastemanagerservice.model.WasteCenterAuthorization;
import com.microservice.wastemanagerservice.model.WasteManager;
import com.microservice.wastemanagerservice.repository.WasteCenterAuthorizationRepository;
import com.microservice.wastemanagerservice.repository.WasteManagerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WasteCenterAuthorizationService {

    private final WasteCenterAuthorizationRepository repository;
    private final WasteCenterAuthorizationMapper authorizationMapper;
    private final WasteManagerRepository wasteManagerRepository;
    private static final String NO_WASTE_MANAGER = "There is no waste manager with that id";
    private static final String NO_WASTE_CENTER = "There is no waste center authorization with that id";

    public WasteCenterAuthorizationService(WasteCenterAuthorizationRepository repository, WasteCenterAuthorizationMapper authorizationMapper, WasteManagerRepository wasteManagerRepository) {
        this.repository = repository;
        this.authorizationMapper = authorizationMapper;
        this.wasteManagerRepository = wasteManagerRepository;
    }

    // Create a new authorization
    public WasteCenterAuthorizationDto createAuthorization(WasteCenterAuthorizationRequest request)
            throws WasteManagerNotFoundException{
        Optional<WasteManager> wasteManagerOptional = wasteManagerRepository.findById(request.getWasteManagerId());
        if(wasteManagerOptional.isPresent()){
            WasteCenterAuthorization wasteCenterAuthorization = WasteCenterAuthorization.builder()
                    .authorizationNumber(request.getAuthorizationNumber())
                    .wasteManager(wasteManagerOptional.get())
                    .build();
            return authorizationMapper.mapToDto(repository.save(wasteCenterAuthorization));
        }
        throw new WasteManagerNotFoundException(NO_WASTE_MANAGER);
    }

    // Find an authorization by ID
    public WasteCenterAuthorizationDto findAuthorizationById(Long id) throws WasteCenterAuthorizationNotFoundException {
        return repository.findById(id)
                .map(authorizationMapper::mapToDto)
                .orElseThrow(() -> new WasteCenterAuthorizationNotFoundException(NO_WASTE_CENTER));
    }

    public WasteCenterAuthorizationDto updateAuthorization(WasteCenterAuthorizationDto dto) throws WasteCenterAuthorizationNotFoundException {
        return repository.findById(dto.getId())
                .map(existingManager -> {
                    WasteCenterAuthorization updatedWasteCenterAuthorization = WasteCenterAuthorization.builder()
                            .id(existingManager.getId())
                            .authorizationNumber(dto.getAuthorizationNumber())
                            .wasteManager(existingManager.getWasteManager())
                            .build();
                    return authorizationMapper.mapToDto(repository.save(updatedWasteCenterAuthorization));
                }).orElseThrow(() -> new WasteCenterAuthorizationNotFoundException(NO_WASTE_CENTER));
    }

    // Delete an authorization
    public void deleteAuthorization(Long id) throws WasteCenterAuthorizationNotFoundException {
        Optional<WasteCenterAuthorization> wasteCenterAuthorizationOptional = repository.findById(id);
        if(wasteCenterAuthorizationOptional.isPresent()){
            repository.deleteById(id);
        }else{
            throw new WasteCenterAuthorizationNotFoundException(NO_WASTE_CENTER);
        }
    }

    // List all authorizations
    public List<WasteCenterAuthorizationDto> findAllAuthorizations() {
        return authorizationMapper.mapToDtoList(repository.findAll());
    }
}
