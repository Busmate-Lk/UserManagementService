package com.busmatelk.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "conductor_profile")
public class Conductor {
    @Id
    @Column(name = "user_id")
    private UUID userId;
    private String employee_id;
    private String assign_operator_id;
    private String shift_status;
}
