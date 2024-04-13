package com.microservice.wastemanagerservice.repository;

import com.microservice.wastemanagerservice.model.WasteManager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WasteManagerRepository extends JpaRepository<WasteManager, Long> {
}
