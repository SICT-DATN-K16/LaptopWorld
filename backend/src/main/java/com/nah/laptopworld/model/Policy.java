package com.nah.laptopworld.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "policy")
@Entity
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Tiêu đề không được để trống")
    private String title;

    @Column(unique = true)
    @NotEmpty(message = "Slug không được để trống")
    private String slug;

    @NotEmpty(message = "Mô tả thương hệu không được để trống")
    @Lob
    private String content;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}