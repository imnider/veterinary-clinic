package com.ug.veterinary.veterinary_clinic.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "medical_record_treatment")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalRecordTreatment {

    @EmbeddedId
    private MedicalRecordTreatmentId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("medicalRecordId")
    @JoinColumn(name = "medical_record_id")
    private MedicalRecord medicalRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("treatmentId")
    @JoinColumn(name = "treatment_id")
    private Treatment treatment;

    @Column(name = "dosage")
    private String dosage;

    @Column(name = "treatment_duration")
    private String treatmentDuration;
}
