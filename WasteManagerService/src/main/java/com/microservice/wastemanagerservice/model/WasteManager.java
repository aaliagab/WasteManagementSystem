package com.microservice.wastemanagerservice.model;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "waste_manager")
public class WasteManager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String nif;

    @OneToMany(targetEntity = WasteCenterAuthorization.class,fetch = FetchType.LAZY, mappedBy = "wasteManager")
    @Builder.Default
    private List<WasteCenterAuthorization> authorizations = new ArrayList<>();

    private Boolean isEnabled = true;

    @Version
    private Long version;

    @CreationTimestamp
    private Date createdDate;

    @UpdateTimestamp
    private Date lastModifiedDate;
}
