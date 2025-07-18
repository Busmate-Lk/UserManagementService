package com.busmatelk.backend.controller;


import com.busmatelk.backend.dto.PassengerDTO;
import com.busmatelk.backend.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/passenger")
@CrossOrigin
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @PostMapping(path = "/register")
    public String addPassenger(@RequestBody PassengerDTO passengerDTO) {
        //System.out.println(passengerDTO);
        passengerService.createPassenger(passengerDTO);
        return "success";
    }

    @GetMapping(path = "/profile")
    public PassengerDTO getPassengerById(@RequestParam UUID userId) {
        return  passengerService.getPassengerById(userId);
    }
}
