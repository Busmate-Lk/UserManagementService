package com.busmatelk.backend.service;

import com.busmatelk.backend.dto.fleetOperatorDTO;

import java.util.UUID;

public interface fleetOperatorProfileService {

void addfleetOperatorProfile( fleetOperatorDTO fleetOperatorDTO);

    fleetOperatorDTO getFleetoperatorById(UUID userId);
}
