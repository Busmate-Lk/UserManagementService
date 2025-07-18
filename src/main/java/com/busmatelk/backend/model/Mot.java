package com.busmatelk.backend.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mot {
    @Id
    @Column(name = "user_id")
    private UUID userId;
    private String employee_id;

}
