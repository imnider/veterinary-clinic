package com.ug.veterinary.veterinary_clinic.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class Auditable extends TimestampedAuditable {

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}