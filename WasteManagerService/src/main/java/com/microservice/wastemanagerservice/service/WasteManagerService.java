package com.microservice.wastemanagerservice.service;

import com.microservice.wastemanagerservice.dto.WasteManagerDto;
import com.microservice.wastemanagerservice.dto.mapper.WasteCenterAuthorizationMapper;
import com.microservice.wastemanagerservice.dto.mapper.WasteManagerMapper;
import com.microservice.wastemanagerservice.model.WasteManager;
import com.microservice.wastemanagerservice.repository.WasteManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WasteManagerService {

    @Autowired
    private WasteManagerRepository repository;

    public WasteManagerDto createWasteManager(WasteManagerDto dto) {
        WasteManager wasteManager = WasteManager.builder()
                .name(dto.getName())
                .nif(dto.getNif())
                .authorizations(WasteCenterAuthorizationMapper.INSTANCE.dtoToEntity(dto.getAuthorizations()))
                .isEnabled(dto.getIsEnabled())
                .build();
        return WasteManagerMapper.INSTANCE.entityToDto(repository.save(wasteManager));
    }

    public WasteManagerDto updateWasteManager(Long id, WasteManagerDto dto) {
        return  repository.findById(id)
                .map(existingManager -> {
                    WasteManager updatedManager = WasteManager.builder()
                            .id(existingManager.getId())
                            .name(dto.getName())
                            .nif(dto.getNif())
                            .authorizations(WasteCenterAuthorizationMapper.INSTANCE.dtoToEntity(dto.getAuthorizations()))
                            .isEnabled(dto.getIsEnabled())
                            .build();
                    return WasteManagerMapper.INSTANCE.entityToDto(repository.save(updatedManager));
                }).orElse(null);
    }

    public WasteManagerDto findById(Long id) {
        return repository.findById(id)
                .map(WasteManagerMapper.INSTANCE::entityToDto)
                .orElse(null);
    }

    public void deleteWasteManager(Long id) {
        repository.deleteById(id);
    }

    public List<WasteManagerDto> findAll(){
        return WasteManagerMapper.INSTANCE.entityToDto(repository.findAll());
    }
}
