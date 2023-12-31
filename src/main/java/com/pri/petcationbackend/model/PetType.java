package com.pri.petcationbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="pet_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PetType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Pet_type_id")
    private Long petTypeId;
    @NotNull
    @NotEmpty
    @Column(name = "Name")
    private String name;

    public PetType(String name) {
        this.name = name;
    }
}
