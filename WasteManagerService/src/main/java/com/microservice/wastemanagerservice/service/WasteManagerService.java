package com.microservice.wastemanagerservice.service;

import com.microservice.wastemanagerservice.dto.WasteManagerDto;
import com.microservice.wastemanagerservice.dto.mapper.WasteCenterAuthorizationMapper;
import com.microservice.wastemanagerservice.dto.mapper.WasteManagerMapper;
import com.microservice.wastemanagerservice.model.WasteCenterAuthorization;
import com.microservice.wastemanagerservice.model.WasteManager;
import com.microservice.wastemanagerservice.repository.WasteCenterAuthorizationRepository;
import com.microservice.wastemanagerservice.repository.WasteManagerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WasteManagerService {

    private final WasteManagerRepository repository;
    private final WasteManagerMapper wasteManagerMapper;
    private final WasteCenterAuthorizationMapper wasteCenterAuthorizationMapper;
    private final WasteCenterAuthorizationRepository wasteCenterAuthorizationRepository;

    private static final String NO_WASTE_MANAGER = "There is no waste manager with that id";

    public WasteManagerService(WasteManagerRepository repository, WasteManagerMapper wasteManagerMapper, WasteCenterAuthorizationMapper wasteCenterAuthorizationMapper, WasteCenterAuthorizationRepository wasteCenterAuthorizationRepository) {
        this.repository = repository;
        this.wasteManagerMapper = wasteManagerMapper;
        this.wasteCenterAuthorizationMapper = wasteCenterAuthorizationMapper;
        this.wasteCenterAuthorizationRepository = wasteCenterAuthorizationRepository;
    }

    public WasteManagerDto createWasteManager(WasteManagerDto dto) {
        WasteManager wasteManager = WasteManager.builder()
                .name(dto.getName())
                .nif(dto.getNif())
                .isEnabled(dto.getIsEnabled())
                .build();
        wasteManager = repository.save(wasteManager); // Guardar para obtener un ID generado

        if (dto.getAuthorizations() != null && !dto.getAuthorizations().isEmpty()) {
            WasteManager finalWasteManager = wasteManager;
            List<WasteCenterAuthorization> wasteCenterAuthorizationList = dto.getAuthorizations().stream()
                    .map(authDto -> {
                        WasteCenterAuthorization auth = WasteCenterAuthorization.builder()
                                .authorizationNumber(authDto.getAuthorizationNumber())
                                .wasteManager(finalWasteManager)
                                .build();
                        return wasteCenterAuthorizationRepository.save(auth);
                    })
                    .collect(Collectors.toList());

            wasteManager.setAuthorizations(wasteCenterAuthorizationList);
            wasteManager = repository.save(wasteManager);
        }

        return wasteManagerMapper.mapToDto(wasteManager);
    }


    public WasteManagerDto updateWasteManager(Long id, WasteManagerDto dto) {
        return  repository.findById(id)
                .map(existingManager -> {
                    WasteManager updatedManager = WasteManager.builder()
                            .id(existingManager.getId())
                            .name(dto.getName())
                            .nif(dto.getNif())
                            .authorizations(existingManager.getAuthorizations())
                            .isEnabled(dto.getIsEnabled())
                            .build();
                    return wasteManagerMapper.mapToDto(repository.save(updatedManager));
                }).orElse(null);
    }

    public WasteManagerDto findById(Long id) {
        return repository.findById(id)
                .map(wasteManagerMapper::mapToDto)
                .orElse(null);
    }

    public void deleteWasteManager(Long id) {
        repository.deleteById(id);
    }

    public List<WasteManagerDto> findAll(){
        return wasteManagerMapper.mapToDtoList(repository.findAll());
    }
}
