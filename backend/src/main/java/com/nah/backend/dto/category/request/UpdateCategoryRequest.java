package com.nah.backend.dto.category.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryRequest {
    @NotBlank(message = "Tên danh mục không được để trống")
    private String categoryName;
    
    private Integer parentId;
} 