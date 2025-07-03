package com.busmatelk.backend.service.impl;

import com.busmatelk.backend.dto.fleetOperatorDTO;
import com.busmatelk.backend.model.fleetOperatorModel;
import com.busmatelk.backend.repository.fleetOperatorRepo;
import com.busmatelk.backend.service.fleetOperatorProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class fleetOperatorServiceIMPL implements fleetOperatorProfileService {

    @Autowired
    private fleetOperatorRepo fleetOperatorRepo;

    @Override
    public void addfleetOperatorProfile( fleetOperatorDTO fleetOperatorDTO) {

        fleetOperatorModel fleetOperatorModel = new fleetOperatorModel(
                fleetOperatorDTO.getOperator_type(),
                fleetOperatorDTO.getOrganization_name(),
                fleetOperatorDTO.getRegion(),
                fleetOperatorDTO.getRegistration_id(),
                fleetOperatorDTO.getContact_details()

        );

        fleetOperatorRepo.save(fleetOperatorModel);


    }
}
