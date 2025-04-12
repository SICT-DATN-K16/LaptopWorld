package com.nah.laptopworld.repository;

import com.nah.laptopworld.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long>{
    void deleteAllByLaptopModelId(Long productId);
}
