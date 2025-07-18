package com.busmatelk.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "fleetoperator_profile")
public class fleetOperatorModel {

    @Id
    @Column(name = "user_id")
    private UUID userId; // Same as users.user_id (FK manually handled)

    @Column(name = "operator_type")
    private String operatorType;

    @Column(name = "organization_name")
    private String organizationName;

    @Column(name = "region")
    private String region;

    @Column(name = "registration_id")
    private String registrationId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private List<String> contact_details;

    public fleetOperatorModel(String operatorType, String organizationName, String region, String registrationId, List<String> contactDetails) {


    }
}
