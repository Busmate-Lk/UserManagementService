package com.busmatelk.backend.dto;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class fleetOperatorDTO {

    // From `users` table
    private UUID userId;
    private String fullName;
    private String username;
    private String email;
    private String phoneNumber;  // Added phoneNumber field
//    private String role;
    private String accountStatus;
    private Boolean isVerified;

    // From `fleetoperator_profile` table
    private String operatorType;
    private String organizationName;
    private String region;
    private String registrationId;

    // passward set for dto
    private String password;



}
