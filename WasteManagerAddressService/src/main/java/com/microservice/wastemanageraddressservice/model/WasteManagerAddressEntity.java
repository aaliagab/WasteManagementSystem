package com.microservice.wastemanageraddressservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "waste_manager_address")
public class WasteManagerAddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    private Boolean isEnabled = true;

    private Long version = 0L;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    // Reference the relationship with WasteManager that is managed by another microservice
    private Long wasteManagerId;
}
