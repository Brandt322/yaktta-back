package com.Yaktta.Disco.models.entities;

import com.Yaktta.Disco.models.entities.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "roles")
public class Role {
    @Id
    private Long id_rol;
    private String rol;

    @ManyToMany(mappedBy = "roles")
    @JsonManagedReference
    private List<User> users;
}
