package com.ug.veterinary.veterinary_clinic.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "medical_record")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalRecord extends TimestampedAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medical_record_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", nullable = false, unique = true)
    private Appointment appointment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veterinarian_id", nullable = false)
    private AppUser veterinarian;

    @Column(name = "record_date", nullable = false)
    private LocalDateTime recordDate;

    @Column(name = "diagnosis", nullable = false)
    private String diagnosis;

    @Column(name = "notes")
    private String notes;

    @Column(name = "recorded_weight")
    private BigDecimal recordedWeight;
}
