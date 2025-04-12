package com.nah.laptopworld.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LaptopDetailDTO {
    private Long modelId;
    private String modelName;
    private String brand;
    private String description;
    private Long variantId;
    private String variantName;
    private double price;
    private int quantity;
}