package com.busmatelk.backend.service.impl;

import com.busmatelk.backend.dto.TimekeeperDTO;
import com.busmatelk.backend.service.TimekeeperService;
import com.busmatelk.backend.model.Timekeeper;
import com.busmatelk.backend.model.User;
import com.busmatelk.backend.repository.TimekeeperRepo;
import com.busmatelk.backend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TimekeeperServiceIMPL implements TimekeeperService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TimekeeperRepo timekeeperRepo;

    @Value("${supabase.anon-key}")
    private String supabaseAnonKey;

    @Override
    @Transactional
    public void createtimekeeper(TimekeeperDTO signupDTO) {
        try {
            // Step 1: Register user with Supabase Auth
            HttpClient client = HttpClient.newHttpClient();
            String requestBody = String.format("""
                {
                    "email": "%s",
                    "password": "%s"
                }
            """, signupDTO.getEmail(), signupDTO.getPassword());

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://gvxbzcxjueghvrtsfdxc.supabase.co/auth/v1/signup"))
                    .header("Content-Type", "application/json")
                    .header("apikey", supabaseAnonKey)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200 && response.statusCode() != 201) {
                throw new RuntimeException("Supabase signup failed: " + response.body());
            }

            // Step 2: Extract userId from response JSON
            String responseBody = response.body();
            String userIdString = extractUserIdFromJson(responseBody);
            UUID userId = UUID.fromString(userIdString);

            // Step 3: Save to User table
            User user = new User();
            user.setUserId(userId);
            user.setFullName(signupDTO.getFullname());
            user.setEmail(signupDTO.getEmail());
            user.setRole("Timekeeper");
            user.setCreatedAt(Instant.now());
            user.setPhoneNumber(signupDTO.getPhonenumber());
            user.setIsVerified(false);
            user.setAccountStatus("ACTIVE");
            user = userRepo.save(user);

            // Step 4: Save to Timekeeper table
            Timekeeper timekeeper = new Timekeeper();
            timekeeper.setUser(user);
            timekeeper.setAssignStand(signupDTO.getAssign_stand());
            timekeeper.setNic(signupDTO.getNic());
            timekeeper.setProvince(signupDTO.getProvince());
            timekeeperRepo.save(timekeeper);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to create timekeeper: " + e.getMessage(), e);
        }
    }

    private String extractUserIdFromJson(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        if (root.has("id")) {
            return root.get("id").asText();
        } else if (root.has("user") && root.get("user").has("id")) {
            return root.get("user").get("id").asText();
        } else {
            throw new RuntimeException("Unexpected Supabase response: " + json);
        }
    }
}
