package com.busmatelk.backend.controller;

import com.busmatelk.backend.config.SecurityConfig;
import com.busmatelk.backend.dto.fleetOperatorDTO;
import com.busmatelk.backend.service.fleetOperatorProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@CrossOrigin
public class fleetOperatorController {

    @Autowired
    private fleetOperatorProfileService fleetOperatorProfileService;

    @PostMapping(path = "/create")
    public String addFleetOperator(@RequestBody fleetOperatorDTO fleetOperatorDTO, @RequestHeader("Authorization") String token) {


        fleetOperatorProfileService.addfleetOperatorProfile(fleetOperatorDTO);
        return "success";
    }
}
