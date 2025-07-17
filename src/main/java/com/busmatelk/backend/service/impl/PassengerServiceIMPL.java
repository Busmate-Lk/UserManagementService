package com.busmatelk.backend.service.impl;

import com.busmatelk.backend.dto.PassengerDTO;
import com.busmatelk.backend.model.Passenger;
import com.busmatelk.backend.model.User;
import com.busmatelk.backend.repository.PassengerRepo;
import com.busmatelk.backend.repository.UserRepo;
import com.busmatelk.backend.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class PassengerServiceIMPL implements PassengerService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PassengerRepo passengerRepo;

    @Override
    public void createPassenger(PassengerDTO passengerDTO) {

        // Map to User
        User user = new User();
//        user.setUserId(UUID.randomUUID());
        user.setUserId(UUID.fromString("58942478-16f7-4afa-9b00-1fbe9ef4bad7"));

        user.setFullName(passengerDTO.getFullName());
        user.setUsername(passengerDTO.getUsername());
        user.setEmail(passengerDTO.getEmail());
        user.setRole(passengerDTO.getRole());
        user.setAccountStatus(passengerDTO.getAccountStatus());
        user.setIsVerified(passengerDTO.getIsVerified());
        user.setCreatedAt(Instant.now());

        userRepo.save(user);

        Passenger passenger = new Passenger();
        passenger.setUserId(user.getUserId());
        passenger.setNotification_preferences(passengerDTO.getNotification_preferences());

        passengerRepo.save(passenger);
    }

    @Override
    public PassengerDTO getPassengerById(UUID userId) {

        User user = userRepo.findById(userId).get();
        Passenger passenger = passengerRepo.findById(userId).get();
        PassengerDTO passengerDTO = new PassengerDTO();
        passengerDTO.setUserId(user.getUserId());
        passengerDTO.setFullName(user.getFullName());
        passengerDTO.setUsername(user.getUsername());
        passengerDTO.setEmail(user.getEmail());
        passengerDTO.setRole(user.getRole());
        passengerDTO.setAccountStatus(user.getAccountStatus());
        passengerDTO.setIsVerified(user.getIsVerified());

        passengerDTO.setNotification_preferences(passenger.getNotification_preferences());

        return passengerDTO;

    }
}
