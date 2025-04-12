package com.nah.laptopworld.dto.response;

import com.nah.laptopworld.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LaptopDetailResponse {
    // Model attributes
    private Long modelId;
    private String modelName;
    private String detailDesc;
    private String shortDesc;
    private String target;
    private boolean status;
    private String brandName;
    private List<String> productImages;
    private List<Comment> comments;

    // Variant attributes
    private Long variantId;
    private String variantName;
    private long quantity;
    private long sold;
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