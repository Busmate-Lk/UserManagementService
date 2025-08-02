package com.busmatelk.backend.controller;

import com.busmatelk.backend.dto.ConductorDTO;
import com.busmatelk.backend.service.ConductorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/conductor")
@CrossOrigin
public class ConductorController {

    @Autowired
    private ConductorService conductorService;

    @PostMapping(path = "/register")
    public String createConductor(@RequestBody ConductorDTO conductorDTO) {
        System.out.println(conductorDTO);
        conductorService.createConductor(conductorDTO);
        return "success";
    }

    @GetMapping(path="/profile")
    public ConductorDTO getConductorsById( @RequestParam UUID userId) {
//        System.out.println(conductorService.getconductorsById(userId));
        return conductorService.getconductorsById(userId);

    }


    @PutMapping(path = "/update")
    public ConductorDTO updateConductor(@RequestBody ConductorDTO conductorDTO, @RequestParam UUID userId) {
        return conductorService.updateconductor(conductorDTO,userId);
    }
}
