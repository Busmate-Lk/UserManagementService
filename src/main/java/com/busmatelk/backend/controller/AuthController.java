package com.busmatelk.backend.controller;

import com.busmatelk.backend.dto.request.LoginRequestDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Value("${supabase.anon-key}")
    private String supabaseAnonKey;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        RestTemplate restTemplate = new RestTemplate();
//        System.out.println(request);

        String supabaseUrl = "https://gvxbzcxjueghvrtsfdxc.supabase.co/auth/v1/token?grant_type=password";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", supabaseAnonKey);

        Map<String, String> payload = Map.of(
                "email", request.getEmail(),
                "password", request.getPassword()
        );

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(payload, headers);
//        System.out.println(payload);
//        System.out.println(headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(supabaseUrl, entity, Map.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Login failed", "message", e.getMessage()));
        }
    }
}
