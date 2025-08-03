package com.busmatelk.backend.controller;


import com.busmatelk.backend.dto.TimekeeperDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.busmatelk.backend.service.TimekeeperService;

import java.util.UUID;

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

    @GetMapping("/profile/{userId}")
    public ResponseEntity<?> getTimekeeperById(@PathVariable UUID userId) {
        try {
            TimekeeperDTO timekeeper = timekeeperService.getTimekeeperById(userId);
            return ResponseEntity.ok(timekeeper);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
