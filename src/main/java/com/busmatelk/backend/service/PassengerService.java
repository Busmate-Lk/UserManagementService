package com.busmatelk.backend.service;

import com.busmatelk.backend.dto.PassengerDTO;

import java.util.UUID;

public interface PassengerService {


    void createPassenger(PassengerDTO passengerDTO);


    PassengerDTO getPassengerById(UUID userId);
}
