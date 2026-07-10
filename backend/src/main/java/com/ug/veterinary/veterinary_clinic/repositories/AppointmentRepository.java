package com.ug.veterinary.veterinary_clinic.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ug.veterinary.veterinary_clinic.entities.Appointment;
import com.ug.veterinary.veterinary_clinic.enums.AppointmentStatusEnum;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByStatus(AppointmentStatusEnum status);
    List<Appointment> findByPetId(Integer petId);
    List<Appointment> findByPetIdAndStatus(Integer petId, AppointmentStatusEnum status);
    List<Appointment> findByVeterinarianId(Integer veterinarianId);
    List<Appointment> findByVeterinarianIdAndStatus(Integer veterinarianId, AppointmentStatusEnum status);
    List<Appointment> findByPetOwnerId(Integer ownerId);
    List<Appointment> findByPetOwnerIdAndStatus(Integer ownerId, AppointmentStatusEnum status);
}
