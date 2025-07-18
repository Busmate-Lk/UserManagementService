package com.busmatelk.backend.controller;

import com.busmatelk.backend.config.SecurityConfig;
import com.busmatelk.backend.dto.fleetOperatorDTO;
import com.busmatelk.backend.service.fleetOperatorProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/fleetoperator")
@CrossOrigin
public class fleetOperatorController {

    @Autowired
    private fleetOperatorProfileService fleetOperatorProfileService;

    @PostMapping("/register")
    public ResponseEntity<?> registerFleetOperator(@RequestBody fleetOperatorDTO fleetOperatorDTO) {
        try {
            fleetOperatorProfileService.addfleetOperatorProfile(fleetOperatorDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Fleet operator registered successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred: " + e.getMessage());
        }

    }

    @GetMapping(path = "/profile")
    public fleetOperatorDTO getFleetOperatorProfile(@RequestParam UUID userId){
       return fleetOperatorProfileService.getFleetoperatorById(userId);
    }


}
