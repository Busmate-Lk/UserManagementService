package com.busmatelk.backend.model;

import jakarta.persistence.*;
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
    private String NicNumber;
    private String Dateofbirth;
    private String pr_img_path;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")  // This is both PK and FK
    private User user;

}
