package com.microservice.wastemanageraddressservice.repository;

import com.microservice.wastemanageraddressservice.model.WasteManagerAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WasteManagerAddressRepository extends JpaRepository<WasteManagerAddressEntity, Long> {
    List<WasteManagerAddressEntity> findByWasteManagerId(Long wasteManagerId);
}
