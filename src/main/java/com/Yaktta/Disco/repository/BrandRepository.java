package com.Yaktta.Disco.repository;

import com.Yaktta.Disco.models.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    Brand findByBrandName(String brandName);
}
