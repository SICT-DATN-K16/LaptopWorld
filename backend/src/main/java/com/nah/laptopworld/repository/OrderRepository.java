package com.nah.laptopworld.repository;

import com.nah.laptopworld.model.Order;
import com.nah.laptopworld.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
    List<Order> findByUser(User user);

    Page<Order> findAll(Pageable pageable);

    @Query(value = "SELECT DATE(o.createdAt) as date, SUM(d.quantity) as counts " +
            "FROM Order o " +
            "INNER JOIN OrderDetail d ON o.id = d.order.id " +
            "WHERE o.createdAt BETWEEN :startDate AND :endDate " +
            "GROUP BY DATE(o.createdAt)" +
            "ORDER BY DATE(o.createdAt) ASC "
    )
    List<Map<String, Object>> getSalesStatistics(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);

    @Query("SELECT m.brand.name as brand, COUNT(m.brand.name) as counts " +
            "FROM Order o " +
            "INNER JOIN OrderDetail d ON o.id = d.order.id " +
            "INNER JOIN LaptopVariant p ON d.laptopVariant.id = p.id " +
            "INNER JOIN LaptopModel m ON p.laptopModel.id = m.id " +
            "WHERE o.createdAt BETWEEN :startDate AND :endDate " +
            "GROUP BY (m.brand.name)")
    List<Map<String, Object>> getSalesStatisticsByBrand(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);

    @Query("SELECT SUM(d.quantity * d.price) as amount " +
            "FROM Order o " +
            "INNER JOIN OrderDetail d ON o.id = d.order.id " +
            "WHERE o.createdAt BETWEEN :startDate AND :endDate "
    )
    Optional<Double> getTotalAmountByMonth(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);

    @Query(" SELECT o " +
            "FROM Order o " +
            "WHERE (?1 IS NULL OR LOWER(o.status) LIKE CONCAT('%',LOWER(?1),'%')) "
    )
    Page<Order> filterOrderByStatus(String status, Pageable pageable);
}
