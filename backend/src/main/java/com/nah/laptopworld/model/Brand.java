package com.nah.laptopworld.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "brands")
@Entity
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Tên thương hiệu không được để trống")
    private String name;

    @NotEmpty(message = "Logo thương hiệu không được để trống")
    private String logo;

    @NotEmpty(message = "Mô tả thương hệu không được để trống")
    @Lob
    private String description;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    private List<LaptopModel> laptopModels;
}