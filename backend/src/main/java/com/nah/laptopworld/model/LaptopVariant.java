package com.nah.laptopworld.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "laptop_variants")
@Entity
public class LaptopVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @NotEmpty(message = "Tên biến thể không được để trống")
    private String variantName;
    @ManyToOne
    @JoinColumn(name = "laptop_model_id")
    private LaptopModel laptopModel;
    @Min(value = 1, message = "Số lượng sản phẩm phải lớn hơn hoặc bằng 1")
    private long quantity;
    private long sold;
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá sản phẩm phải lớn hơn 0")
    private double price;
    private String processorBrand;
    private String processor;
    private int ram;
    private String graphicCardBrand;
    private String graphicCard;
    private int storage;
    private String display;
    private double weight;
}
