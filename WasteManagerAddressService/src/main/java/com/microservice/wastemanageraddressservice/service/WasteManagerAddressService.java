package com.microservice.wastemanageraddressservice.service;

import com.microservice.wastemanageraddressservice.client.WasteManagerServiceClient;
import com.microservice.wastemanageraddressservice.dto.WasteManagerAddressDto;
import com.microservice.wastemanageraddressservice.dto.WasteManagerDto;
import com.microservice.wastemanageraddressservice.dto.mapper.WasteManagerAddressMapper;
import com.microservice.wastemanageraddressservice.dto.mapper.request.WasteManagerAddressRequest;
import com.microservice.wastemanageraddressservice.exception.WasteManagerAddressNotFoundException;
import com.microservice.wastemanageraddressservice.exception.WasteManagerNotFoundException;
import com.microservice.wastemanageraddressservice.exception.WasteManagerRepeatedException;
import com.microservice.wastemanageraddressservice.model.WasteManagerAddressEntity;
import com.microservice.wastemanageraddressservice.repository.WasteManagerAddressRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WasteManagerAddressService {

    private final WasteManagerAddressRepository repository;
    private final WasteManagerServiceClient wasteManagerServiceClient;
    private final WasteManagerAddressMapper mapper;
    @Value("${waste.manager.service.url}")
    private String wasteManagerServiceUrl;
    private static final String NO_WASTE_MANAGER_ADDRESS = "There is no waste manager address with that id";
    private static final String NO_WASTE_MANAGER = "Waste Manager not found with ID: ";

    public WasteManagerAddressService(WasteManagerAddressRepository repository, WasteManagerServiceClient wasteManagerServiceClient, WasteManagerAddressMapper mapper) {
        this.repository = repository;
        this.wasteManagerServiceClient = wasteManagerServiceClient;
        this.mapper = mapper;
    }

    public WasteManagerAddressDto createAddress(WasteManagerAddressDto dto) throws WasteManagerNotFoundException, WasteManagerRepeatedException {
        validateWasteManagerExists(dto.getWasteManagerId());
        if(getAddressByWasteManagerId(dto.getWasteManagerId())!=null){
            throw new WasteManagerRepeatedException("There is already an address assigned to that Waste Manager.");
        }
        WasteManagerAddressEntity entity = WasteManagerAddressEntity.builder()
                .id(dto.getId())
                .address(dto.getAddress())
                .wasteManagerId(dto.getWasteManagerId())
                .createdDate(dto.getCreatedDate())
                .lastModifiedDate(dto.getLastModifiedDate())
                .version(dto.getVersion())
                .isEnabled(dto.getIsEnabled())
                .build();
        entity = repository.save(entity);
        return mapper.mapToDto(entity);
    }

    public List<WasteManagerAddressDto> getAllAddresses() {
        return mapper.mapToDtoList(repository.findAll());
    }

    public WasteManagerAddressDto getAddressByWasteManagerId(Long id) throws WasteManagerNotFoundException {
        return repository.findByWasteManagerId(id).size()!=0?
                mapper.mapToDto(repository.findByWasteManagerId(id).get(0)):null;
    }

    public WasteManagerAddressDto getAddressById(Long id) throws WasteManagerAddressNotFoundException {
        return repository.findById(id)
                .map(mapper::mapToDto)
                .orElseThrow(() -> new WasteManagerAddressNotFoundException(NO_WASTE_MANAGER_ADDRESS));
    }

    public WasteManagerAddressDto updateAddress(WasteManagerAddressRequest request) throws WasteManagerAddressNotFoundException {
        WasteManagerAddressEntity existingManagerAddress = repository.findById(request.getId())
                .orElseThrow(() -> new WasteManagerAddressNotFoundException(NO_WASTE_MANAGER_ADDRESS));

        existingManagerAddress.setAddress(request.getAddress());
        existingManagerAddress.setVersion(request.getVersion());
        existingManagerAddress.setIsEnabled(request.getIsEnabled());

        WasteManagerAddressEntity saved = repository.save(existingManagerAddress);

        return mapper.mapToDto(saved);
    }

    public void deleteAddress(Long id) throws WasteManagerAddressNotFoundException {
        Optional<WasteManagerAddressEntity> optional = repository.findById(id);
        if(optional.isPresent()){
            repository.deleteById(id);
        }else{
            throw new WasteManagerAddressNotFoundException(NO_WASTE_MANAGER_ADDRESS);
        }
    }

    public void deleteAddressByWasteManagerId(Long id) throws WasteManagerNotFoundException {
        validateWasteManagerExists(id);
        List<Long> addressIds = repository.findByWasteManagerId(id).stream().map(a->a.getId()).collect(Collectors.toList());
        addressIds.forEach(idDel->repository.deleteById(idDel));
    }

    private void validateWasteManagerExists(Long wasteManagerId) throws WasteManagerNotFoundException, ServiceException {
        try {
            ResponseEntity<WasteManagerDto> response = wasteManagerServiceClient.getWasteManagerById(wasteManagerId);
            if(response.getBody() == null || response.getStatusCode() == HttpStatus.NOT_FOUND){
                throw new WasteManagerNotFoundException(NO_WASTE_MANAGER + wasteManagerId);
            }
        } catch (WasteManagerNotFoundException e) {
            throw new WasteManagerNotFoundException(NO_WASTE_MANAGER + wasteManagerId);
        } catch (RestClientException e) {
            throw new ServiceException("Error while verifying Waste Manager existence", e);
        }
    }

}
