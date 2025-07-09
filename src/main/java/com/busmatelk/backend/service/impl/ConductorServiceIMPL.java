package com.busmatelk.backend.service.impl;

import com.busmatelk.backend.dto.ConductorDTO;
import com.busmatelk.backend.model.Conductor;
import com.busmatelk.backend.model.User;
import com.busmatelk.backend.repository.ConductorRepo;
import com.busmatelk.backend.repository.PassengerRepo;
import com.busmatelk.backend.repository.UserRepo;
import com.busmatelk.backend.service.ConductorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class ConductorServiceIMPL implements ConductorService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ConductorRepo conductorRepo;

    @Override
    public void createConductor(ConductorDTO conductorDTO) {
        // Map to User
        User user = new User();
//        user.setUserId(UUID.randomUUID());
        user.setUserId(UUID.fromString("58942478-16f7-4afa-9b00-1fbe9ef4bad7"));

        user.setFullName(conductorDTO.getFullName());
        user.setUsername(conductorDTO.getUsername());
        user.setEmail(conductorDTO.getEmail());
        user.setRole(conductorDTO.getRole());
        user.setAccountStatus(conductorDTO.getAccountStatus());
        user.setIsVerified(conductorDTO.getIsVerified());
        user.setCreatedAt(Instant.now());

        userRepo.save(user);

        Conductor conductor = new Conductor();
        conductor.setUserId(user.getUserId());
        conductor.setAssign_operator_id(conductorDTO.getAssign_operator_id());
        conductor.setShift_status(conductorDTO.getShift_status());

        conductorRepo.save(conductor);


    }
}
