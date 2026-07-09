package com.ug.veterinary.veterinary_clinic.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.ug.veterinary.veterinary_clinic.enums.SexEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "pet")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE pet SET deleted_at = SYSUTCDATETIME() WHERE pet_id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Pet extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private AppUser owner;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "species", nullable = false)
    private String species;

    @Column(name = "breed")
    private String breed;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex", length = 1)
    private SexEnum sex;

    @Column(name = "weight")
    private BigDecimal weight;

    @Column(name = "is_neutered", nullable = false)
    private boolean neutered;
}
