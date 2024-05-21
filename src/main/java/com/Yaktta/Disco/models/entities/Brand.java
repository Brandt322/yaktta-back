package com.Yaktta.Disco.models.entities;

import jakarta.persistence.*;
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

    private String brand_name;

}
