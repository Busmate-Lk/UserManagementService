package com.busmatelk.backend.controller;

import com.busmatelk.backend.dto.ConductorDTO;
import com.busmatelk.backend.dto.PassengerDTO;
import com.busmatelk.backend.service.ConductorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/conductor")
@CrossOrigin
public class ConductorController {

    @Autowired
    private ConductorService conductorService;

    @PostMapping(path = "/register")
    public String addPassenger(@RequestBody ConductorDTO conductorDTO) {
        System.out.println(conductorDTO);
        conductorService.createConductor(conductorDTO);
        return "success";
    }

}
