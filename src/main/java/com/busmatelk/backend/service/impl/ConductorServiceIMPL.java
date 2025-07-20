package com.busmatelk.backend.service.impl;

import com.busmatelk.backend.dto.ConductorDTO;
import com.busmatelk.backend.model.Conductor;
import com.busmatelk.backend.model.User;
import com.busmatelk.backend.repository.ConductorRepo;
import com.busmatelk.backend.repository.UserRepo;
import com.busmatelk.backend.service.ConductorService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    @Value("${supabase.api.key}")
    private String SUPABASE_API_KEY;

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



            // Step 4: Save to your users table
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
            conductor.setPr_img_path(conductor.getPr_img_path());


            conductorRepo.save(conductor);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to create conductor: " + e.getMessage(), e);
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
    public ConductorDTO updateconductor(ConductorDTO conductorDTO, UUID userId, MultipartFile file) {
        // Fetch user
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Fetch existing conductor linked to the user
        Conductor conductor = conductorRepo.findByUserId(user.getUserId());
        if (conductor == null) {
            throw new RuntimeException("Conductor not found for user ID: " + userId);
        }

        //
        // step 3: upload profile image to Supabase Storage

        String SUPABASE_URL = "https://gvxbzcxjueghvrtsfdxc.supabase.co";
        String SUPABASE_BUCKET = "profile-photos";
        try {
            String uploadUrl = SUPABASE_URL + "/storage/v1/object/" + SUPABASE_BUCKET + "/" + file.getOriginalFilename() + "?insert=overwrite";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uploadUrl))
                    .header("Authorization", "Bearer " + SUPABASE_API_KEY)
                    .header("Content-Type", file.getContentType())
                    .PUT(HttpRequest.BodyPublishers.ofByteArray(file.getBytes()))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200 && response.statusCode() != 201) {
                throw new RuntimeException("Failed to upload image: " + response.body());
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to upload image", e);
        }

        // Step 3: Set the profile image path in the DTO
        conductorDTO.setPr_img_path(SUPABASE_URL + "/storage/v1/object/public/" + SUPABASE_BUCKET + "/" + file.getOriginalFilename());



        // Update fields (example fields — change as per your DTO)
        conductor.setShift_status(conductorDTO.getShift_status());
        conductor.setPr_img_path(conductorDTO.getPr_img_path());
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
        dto.setPr_img_path(conductor.getPr_img_path());

        return dto;
    }


}
