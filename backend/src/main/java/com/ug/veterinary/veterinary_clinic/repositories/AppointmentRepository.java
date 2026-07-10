package com.ug.veterinary.veterinary_clinic.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ug.veterinary.veterinary_clinic.entities.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByStatus(String status);
    List<Appointment> findByPetId(Integer petId);
    List<Appointment> findByPetIdAndStatus(Integer petId, String status);
    List<Appointment> findByVeterinarianId(Integer veterinarianId);
    List<Appointment> findByVeterinarianIdAndStatus(Integer veterinarianId, String status);
    List<Appointment> findByPetOwnerId(Integer ownerId);
    List<Appointment> findByPetOwnerIdAndStatus(Integer ownerId, String status);
}
