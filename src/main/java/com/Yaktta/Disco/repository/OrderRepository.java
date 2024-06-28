package com.Yaktta.Disco.repository;

import com.Yaktta.Disco.models.entities.Order;
import com.Yaktta.Disco.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByClientId(User clientId);
}
