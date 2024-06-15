package com.Yaktta.Disco.repository;

import com.Yaktta.Disco.models.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRol(String rol);
}
