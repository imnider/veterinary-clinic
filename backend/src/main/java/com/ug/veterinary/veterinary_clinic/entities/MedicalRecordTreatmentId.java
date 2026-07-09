package com.ug.veterinary.veterinary_clinic.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordTreatmentId implements Serializable {

    @Column(name = "medical_record_id")
    private Integer medicalRecordId;

    @Column(name = "treatment_id")
    private Integer treatmentId;
}