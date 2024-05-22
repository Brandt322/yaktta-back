package com.Yaktta.Disco.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_product", nullable = false, unique = true)
    private Long id;
    @NotBlank(message = "El nombre es requerido")
    private String name;
    @NotBlank(message = "La descripcion es requerida")
    private String description;
    private int stock;
    private double price;
    private double discount;
    @NotBlank(message = "El tipo de producto es requerido")
    private String product_type;
    @Lob
    private byte[] image;
    @ManyToOne
    @JoinColumn(name = "id_brands")
    private Brand id_brands;

}
