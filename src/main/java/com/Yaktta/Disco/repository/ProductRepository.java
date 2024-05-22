package com.Yaktta.Disco.repository;

import com.Yaktta.Disco.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {

}
