package com.busmatelk.backend.service.impl;

import com.busmatelk.backend.dto.ConductorDTO;
import com.busmatelk.backend.model.Conductor;
import com.busmatelk.backend.model.User;
import com.busmatelk.backend.repository.ConductorRepo;
import com.busmatelk.backend.repository.PassengerRepo;
import com.busmatelk.backend.repository.UserRepo;
import com.busmatelk.backend.service.ConductorService;
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
public class ConductorServiceIMPL implements ConductorService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ConductorRepo conductorRepo;

    @Value("${supabase.anon-key}")
    private String supabaseAnonKey;
    @Override
    public void createConductor(ConductorDTO conductorDTO) {

         try {
            // Step 1: Call Supabase Auth API to register user
            HttpClient client = HttpClient.newHttpClient();

            String requestBody = String.format("""
                {
                    "email": "%s",
                    "password": "%s"
                }
            """, conductorDTO.getEmail(), conductorDTO.getPassword());

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

            // Step 3: Save to your users table
            User user = new User();
            user.setUserId(userId);
            user.setFullName(conductorDTO.getFullName());
            user.setUsername(conductorDTO.getUsername());
            user.setEmail(conductorDTO.getEmail());
            user.setRole("Conductor");
            user.setAccountStatus(conductorDTO.getAccountStatus());
            user.setIsVerified(conductorDTO.getIsVerified());
            user.setCreatedAt(Instant.now());
            user.setPhoneNumber(conductorDTO.getPhoneNumber());

            userRepo.save(user);

            // Step 4: Save to conductor_profile
            Conductor conductor = new Conductor();
            conductor.setUserId(userId);
            conductor.setAssign_operator_id(conductorDTO.getAssign_operator_id());
            conductor.setShift_status(conductorDTO.getShift_status());
            conductor.setNicNumber(conductorDTO.getNicNumber());
            conductor.setDateofbirth(conductorDTO.getDateOfBirth());
//            conductor.setPr_img_path(conductorDTO.getP);


            conductorRepo.save(conductor);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to create conductor: " + e.getMessage(), e);
        }


//        // ✅ Helper method to extract user.id from Supabase JSON
//        private String extractUserIdFromJson(String json) throws IOException {
//            ObjectMapper mapper = new ObjectMapper();
//            JsonNode root = mapper.readTree(json);
//
//            if (root.has("user") && root.get("user").has("id")) {
//                return root.get("user").get("id").asText();
//            } else if (root.has("error")) {
//                throw new RuntimeException("Supabase returned error: " + root.get("error").toString());
//            } else {
//                throw new RuntimeException("Unexpected Supabase response: " + json);
//            }
//        }

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
    public ConductorDTO getconductorsById(UUID userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Conductor conductor = conductorRepo.findByUserId(user.getUserId());

        ConductorDTO conductorDTO = new ConductorDTO();

        conductorDTO.setUserId(user.getUserId());
        conductorDTO.setFullName(user.getFullName());
        conductorDTO.setUsername(user.getUsername());
        conductorDTO.setEmail(user.getEmail());
        conductorDTO.setRole(user.getRole());
        conductorDTO.setAccountStatus(user.getAccountStatus());
        conductorDTO.setIsVerified(user.getIsVerified());
        conductorDTO.setPhoneNumber(user.getPhoneNumber());

        conductorDTO.setEmployee_id(conductor.getEmployee_id());
        conductorDTO.setShift_status(conductor.getShift_status());
        conductorDTO.setAssign_operator_id(conductor.getAssign_operator_id());

        System.out.println(conductorDTO);
        return conductorDTO;
    }

    @Override
    public ConductorDTO updateconductor(ConductorDTO conductorDTO, UUID userId) {
        // Fetch user
        User user = userRepo.findById(userId).get();
        if (user == null) {
            throw new RuntimeException("User not found with ID: " + userId);
        }

        // Fetch existing conductor linked to the user
        Conductor conductor = conductorRepo.findByUserId(user.getUserId());
        if (conductor == null) {
            throw new RuntimeException("Conductor not found for user ID: " + userId);
        }

        // Update fields (example fields — change as per your DTO)
        conductor.setShift_status(conductorDTO.getShift_status());
        user.setPhoneNumber(conductorDTO.getPhoneNumber());
        user.setFullName(conductorDTO.getFullName());
        user.setAccountStatus(conductorDTO.getAccountStatus());


        User updatedUser = userRepo.save(user);
        Conductor updatedConductor = conductorRepo.save(conductor);

        ConductorDTO dto = new ConductorDTO();

        // From User entity
        dto.setUserId(user.getUserId());
        dto.setFullName(user.getFullName()); // Assuming separate fields
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setAccountStatus(user.getAccountStatus());
        dto.setIsVerified(user.getIsVerified());
        dto.setPhoneNumber(user.getPhoneNumber());

        // From Conductor entity
        dto.setEmployee_id(conductor.getEmployee_id());
        dto.setAssign_operator_id(conductor.getAssign_operator_id());
        dto.setShift_status(conductor.getShift_status());

        return dto;
    }


}
