package com.ug.veterinary.veterinary_clinic.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "vaccine")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE vaccine SET deleted_at = SYSUTCDATETIME() WHERE vaccine_id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Vaccine extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vaccine_id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "prevented_disease")
    private String preventedDisease;
}
