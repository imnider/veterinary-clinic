package com.ug.veterinary.veterinary_clinic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ug.veterinary.veterinary_clinic.entities.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {}
