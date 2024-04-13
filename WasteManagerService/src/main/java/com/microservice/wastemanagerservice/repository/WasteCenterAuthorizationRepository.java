package com.microservice.wastemanagerservice.repository;

import com.microservice.wastemanagerservice.model.WasteCenterAuthorization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WasteCenterAuthorizationRepository extends JpaRepository<WasteCenterAuthorization, Long> {
}
