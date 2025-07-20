package com.busmatelk.backend.service;

import com.busmatelk.backend.dto.ConductorDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ConductorService {
    void createConductor(ConductorDTO conductorDTO);

    ConductorDTO getconductorsById(UUID userId);

    ConductorDTO updateconductor(ConductorDTO conductorDTO, UUID userId, MultipartFile file);

//    ConductorDTO updateconductor(ConductorDTO conductorDTO, UUID userId);

//    void createConductor(ConductorDTO conductorDTO, MultipartFile file);
}
