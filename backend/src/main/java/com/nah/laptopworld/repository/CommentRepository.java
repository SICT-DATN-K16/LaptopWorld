package com.nah.laptopworld.repository;

import com.nah.laptopworld.model.Comment;
import com.nah.laptopworld.model.LaptopModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByLaptopModel(LaptopModel laptopModel);
    Page<Comment> findAll(Pageable pageable);

    @Query("SELECT c.message FROM Comment c " +
            "INNER JOIN LaptopModel p ON c.laptopModel.id = p.id " +
            "WHERE p.id = :productId")
    List<String> findAllMessageByProductId(Long productId);

    @Query("SELECT c FROM Comment c " +
            "INNER JOIN LaptopModel p ON c.laptopModel.id = p.id " +
            "WHERE p.id = :productId")
    List<Comment> findAllByProductId(Long productId);
}