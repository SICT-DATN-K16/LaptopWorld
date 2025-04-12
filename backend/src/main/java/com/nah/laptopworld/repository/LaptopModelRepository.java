package com.nah.laptopworld.repository;

import com.nah.laptopworld.model.LaptopModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface LaptopModelRepository extends JpaRepository<LaptopModel, Long>, JpaSpecificationExecutor<LaptopModel> {
    LaptopModel findFirstById(long id);
    Page<LaptopModel> findAll(Pageable pageable);
    Page<LaptopModel> findAll(Specification<LaptopModel> spec, Pageable pageable);

    List<LaptopModel> findByNameContaining(String name);
    @Query("SELECT l.brand.name as brand, COUNT(l) as count FROM LaptopModel l GROUP BY l.brand.name")
    List<Map<String, Object>> countProductsByBrand();

    @Query(
            value = " SELECT l.* " +
                    " FROM laptop_models l " +
                    " INNER JOIN brands b ON l.brand_id = b.id " +
                    " WHERE (?1 IS NULL OR LOWER(l.name) LIKE CONCAT('%',LOWER(?1),'%')) " +
                    " AND (?2 IS NULL OR LOWER(b.name) LIKE CONCAT('%',LOWER(?2),'%'))",
            countQuery =" SELECT count(l.id) " +
                    " FROM laptop_models l " +
                    " INNER JOIN brands b ON l.brand_id = b.id " +
                    " WHERE (?1 IS NULL OR LOWER(l.name) LIKE CONCAT('%',LOWER(?1),'%')) " +
                    " AND (?2 IS NULL OR LOWER(b.name) LIKE CONCAT('%',LOWER(?2),'%'))",
            nativeQuery = true
    )
    Page<LaptopModel> filterProductByNameAndBrand(String name, String brand, Pageable pageable);
}
