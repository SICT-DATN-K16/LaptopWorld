package com.nah.laptopworld.repository;

import com.nah.laptopworld.model.LaptopVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaptopVariantRepository extends JpaRepository<LaptopVariant, Long> {
}