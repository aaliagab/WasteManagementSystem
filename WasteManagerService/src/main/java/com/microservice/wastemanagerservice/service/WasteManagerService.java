package com.microservice.wastemanagerservice.service;

import com.microservice.wastemanagerservice.client.AddressServiceClient;
import com.microservice.wastemanagerservice.dto.WasteManagerDto;
import com.microservice.wastemanagerservice.dto.mapper.WasteCenterAuthorizationMapper;
import com.microservice.wastemanagerservice.dto.mapper.WasteManagerMapper;
import com.microservice.wastemanagerservice.dto.request.WasteManagerRequest;
import com.microservice.wastemanagerservice.dto.response.WasteManagerResponse;
import com.microservice.wastemanagerservice.exceptions.WasteManagerNotFoundException;
import com.microservice.wastemanagerservice.model.WasteCenterAuthorization;
import com.microservice.wastemanagerservice.model.WasteManager;
import com.microservice.wastemanagerservice.repository.WasteCenterAuthorizationRepository;
import com.microservice.wastemanagerservice.repository.WasteManagerRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WasteManagerService {

    private final WasteManagerRepository repository;
    private final WasteManagerMapper wasteManagerMapper;
    private final WasteCenterAuthorizationMapper wasteCenterAuthorizationMapper;
    private final WasteCenterAuthorizationRepository wasteCenterAuthorizationRepository;
    private final AddressServiceClient addressServiceClient;

    private static final String NO_WASTE_MANAGER = "There is no waste manager with that id";

    public WasteManagerService(WasteManagerRepository repository, WasteManagerMapper wasteManagerMapper, WasteCenterAuthorizationMapper wasteCenterAuthorizationMapper, WasteCenterAuthorizationRepository wasteCenterAuthorizationRepository, AddressServiceClient addressServiceClient) {
        this.repository = repository;
        this.wasteManagerMapper = wasteManagerMapper;
        this.wasteCenterAuthorizationMapper = wasteCenterAuthorizationMapper;
        this.wasteCenterAuthorizationRepository = wasteCenterAuthorizationRepository;
        this.addressServiceClient = addressServiceClient;
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


    public WasteManagerResponse updateWasteManager(Long id, WasteManagerRequest request) throws WasteManagerNotFoundException, ServiceException {
        WasteManager existingManager = repository.findById(id)
                .orElseThrow(() -> new WasteManagerNotFoundException(NO_WASTE_MANAGER));

        existingManager.setName(request.getName());
        existingManager.setNif(request.getNif());
        existingManager.setIsEnabled(request.getIsEnabled());

        WasteManager savedManager = repository.save(existingManager);

        return wasteManagerMapper.mapToResponse(savedManager);
    }


    public WasteManagerResponse findById(Long id) throws WasteManagerNotFoundException, ServiceException {
        return repository.findById(id)
                        .map(wasteManagerMapper::mapToResponse)
                        .orElseThrow(() -> new WasteManagerNotFoundException(NO_WASTE_MANAGER));
    }


    public void deleteWasteManager(Long id) throws WasteManagerNotFoundException {
        Optional<WasteManager> wasteManagerOptional = repository.findById(id);
        if(wasteManagerOptional.isPresent()){
            List<Long> authorizationsIdList =  wasteManagerOptional.get().getAuthorizations().stream().map(obj->obj.getId()).collect(Collectors.toList());
            authorizationsIdList.forEach(a->wasteCenterAuthorizationRepository.deleteById(a));

            addressServiceClient.deleteAddressByWasteManagerId(id);

            repository.deleteById(id);
        }else{
            throw new WasteManagerNotFoundException(NO_WASTE_MANAGER);
        }
    }

    public List<WasteManagerResponse> findAll(){

        return wasteManagerMapper.mapToResponseList(repository.findAll());
    }
}
