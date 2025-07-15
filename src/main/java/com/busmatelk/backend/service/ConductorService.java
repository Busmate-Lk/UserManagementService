package com.busmatelk.backend.service;

import com.busmatelk.backend.dto.ConductorDTO;

import java.util.UUID;

public interface ConductorService {
    void createConductor(ConductorDTO conductorDTO);



    ConductorDTO getconductorsById(UUID userId);
}
