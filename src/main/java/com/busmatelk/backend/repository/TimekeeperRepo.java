package com.busmatelk.backend.repository;

import com.busmatelk.backend.model.Timekeeper;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TimekeeperRepo extends JpaRepository<Timekeeper, UUID> {
    Timekeeper findByUser_UserId(UUID userId);
}

