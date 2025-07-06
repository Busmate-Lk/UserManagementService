package com.busmatelk.backend.service.impl;

import com.busmatelk.backend.dto.fleetOperatorDTO;
import com.busmatelk.backend.model.User;
import com.busmatelk.backend.model.fleetOperatorModel;
import com.busmatelk.backend.repository.UserRepo;
import com.busmatelk.backend.repository.fleetOperatorRepo;
import com.busmatelk.backend.service.fleetOperatorProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class fleetOperatorServiceIMPL implements fleetOperatorProfileService {

    @Autowired
    private fleetOperatorRepo fleetOperatorRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public void addfleetOperatorProfile( fleetOperatorDTO fleetOperatorDTO) {

        System.out.println(fleetOperatorDTO.getFullName());


        // Map to User
        User user = new User();
//        user.setUserId(UUID.randomUUID());
        user.setUserId(UUID.fromString("04cb2914-382e-4c45-9239-e19f232b0579"));

        user.setFullName(fleetOperatorDTO.getFullName());
        user.setUsername(fleetOperatorDTO.getUsername());
        user.setEmail(fleetOperatorDTO.getEmail());
        user.setRole(fleetOperatorDTO.getRole());
        user.setAccountStatus(fleetOperatorDTO.getAccountStatus());
        user.setIsVerified(fleetOperatorDTO.getIsVerified());
        user.setCreatedAt(Instant.now());

        userRepo.save(user);

        // Map to FleetOperatorProfile
        fleetOperatorModel profile = new fleetOperatorModel();
        profile.setUserId(user.getUserId()); // same UUID
        profile.setOperatorType(fleetOperatorDTO.getOperatorType());
        profile.setOrganizationName(fleetOperatorDTO.getOrganizationName());
        profile.setRegion(fleetOperatorDTO.getRegion());
        profile.setRegistrationId(fleetOperatorDTO.getRegistrationId());
//        profile.setContactDetails(fleetOperatorDTO.getContactDetails());

        fleetOperatorRepo.save(profile);


    }
}
