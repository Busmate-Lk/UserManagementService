package com.busmatelk.backend.controller;


import com.busmatelk.backend.dto.TimekeeperDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busmatelk.backend.service.TimekeeperService;

@RestController
@RequestMapping("/api/timekeeper")
@CrossOrigin
public class TimekeeperController {

    @Autowired
    private TimekeeperService timekeeperService;

    @PostMapping("/register")
    public ResponseEntity<?> signup(@RequestBody TimekeeperDTO signupDTO) {
        try {
            timekeeperService.createtimekeeper(signupDTO);
            return ResponseEntity.ok("Signup successful");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
