package com.busmatelk.backend.service.impl;

import com.busmatelk.backend.dto.fleetOperatorDTO;
import com.busmatelk.backend.model.User;
import com.busmatelk.backend.model.fleetOperatorModel;
import com.busmatelk.backend.repository.UserRepo;
import com.busmatelk.backend.repository.fleetOperatorRepo;
import com.busmatelk.backend.service.fleetOperatorProfileService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.UUID;

@Service
public class fleetOperatorServiceIMPL implements fleetOperatorProfileService {

    @Autowired
    private fleetOperatorRepo fleetOperatorRepo;

    @Autowired
    private UserRepo userRepo;

    @Value("${supabase.anon-key}")
    private String supabaseAnonKey;

    @Override
    public void addfleetOperatorProfile( fleetOperatorDTO fleetOperatorDTO) {

        System.out.println(fleetOperatorDTO.getFullName());

        try {
            // Step 1: Call Supabase Auth API to register user
            HttpClient client = HttpClient.newHttpClient();

            String requestBody = String.format("""
                        {
                            "email": "%s",
                            "password": "%s"
                        }
                    """, fleetOperatorDTO.getEmail(), fleetOperatorDTO.getPassword());

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

            // Map to User
            User user = new User();

            user.setUserId(userId);
            user.setFullName(fleetOperatorDTO.getFullName());
            user.setUsername(fleetOperatorDTO.getUsername());
            user.setEmail(fleetOperatorDTO.getEmail());
            user.setRole("FleetOperator");
            user.setAccountStatus(fleetOperatorDTO.getAccountStatus());
            user.setIsVerified(fleetOperatorDTO.getIsVerified());
            user.setCreatedAt(Instant.now());

            userRepo.save(user);

            // Map to FleetOperatorProfile
            fleetOperatorModel profile = new fleetOperatorModel();
            profile.setUserId(user.getUserId()); // same UUID
            profile.setOperatorType(fleetOperatorDTO.getOperatorType());
            profile.setOrganizationName(fleetOperatorDTO.getOrganizationName());
            profile.setRegion(fleetOperatorDTO.getRegion());
            profile.setRegistrationId(fleetOperatorDTO.getRegistrationId());
//        profile.setContactDetails(fleetOperatorDTO.getContactDetails());

            fleetOperatorRepo.save(profile);


        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Supabase signup failed: " + e);

            }
    }
    private String extractUserIdFromJson(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);

        if (root.has("id")) {
            return root.get("id").asText();  // ✅ user ID is at root
        } else if (root.has("user") && root.get("user").has("id")) {
            // fallback in case Supabase returns "user" wrapping
            return root.get("user").get("id").asText();
        } else {
            throw new RuntimeException("Unexpected Supabase response: " + json);
        }
    }

    @Override
    public fleetOperatorDTO getFleetoperatorById(UUID userId) {
        return null;
    }
}
