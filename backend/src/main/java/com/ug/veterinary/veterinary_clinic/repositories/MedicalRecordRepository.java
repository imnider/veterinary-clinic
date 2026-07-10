package com.ug.veterinary.veterinary_clinic.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ug.veterinary.veterinary_clinic.entities.MedicalRecord;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Integer> {
    Optional<MedicalRecord> findByAppointmentId(Integer appointmentId);
    List<MedicalRecord> findByPetIdOrderByRecordDateDesc(Integer petId);
    boolean existsByAppointmentId(Integer appointmentId);
}
