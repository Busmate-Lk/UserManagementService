package com.busmatelk.backend.repository;

import com.busmatelk.backend.model.Conductor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConductorRepo extends JpaRepository<Conductor, Long> {
}
