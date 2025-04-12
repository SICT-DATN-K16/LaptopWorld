package com.nah.laptopworld.repository;

import com.nah.laptopworld.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    List<Brand> findAll();
    List<Brand> findByNameContainingIgnoreCase(String name);
}