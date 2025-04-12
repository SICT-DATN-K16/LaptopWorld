package com.nah.laptopworld.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "laptop_models")
@Entity
public class LaptopModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Tên sản phẩm không được để trống")
    private String name;

    @NotNull
    @NotEmpty(message = "Mô tả sản phẩm không được để trống")
    @Lob
    private String detailDesc;

    private String target;
    private boolean status;

    @NotEmpty(message = "Mô tả ngắn sản phẩm không được để trống")
    private String shortDesc;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "laptopModel")
    @JsonIgnore
    private List<Comment> comments;

    @OneToMany(mappedBy = "laptopModel")
    private List<LaptopVariant> laptopVariant;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @OneToMany(mappedBy = "laptopModel")
    private List<ProductImage> ProductImages;
}
