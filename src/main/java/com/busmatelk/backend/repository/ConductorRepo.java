package com.busmatelk.backend.repository;

import com.busmatelk.backend.model.Conductor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ConductorRepo extends JpaRepository<Conductor, Long> {
    Conductor findByUserId(UUID userId);


}
