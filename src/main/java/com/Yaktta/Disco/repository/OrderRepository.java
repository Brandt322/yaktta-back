package com.Yaktta.Disco.repository;

import com.Yaktta.Disco.models.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
