package com.Yaktta.Disco.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "brands")
public class Brand {
    @Id()
    @Column(name = "id_brands")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand_name", unique = true)
    @NotBlank(message = "El nombre de la marca es requerido")
    @NotNull(message = "El nombre de la marca es requerido")
    private String brandName;

}
