package com.Yaktta.Disco.repository;

import com.Yaktta.Disco.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserEntityByEmail(String email);
    boolean existsUserEntityByEmail(String email);
}
