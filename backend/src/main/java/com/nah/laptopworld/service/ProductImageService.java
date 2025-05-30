package com.nah.laptopworld.service;

import com.nah.laptopworld.model.ProductImage;
import com.nah.laptopworld.repository.ProductImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductImageService {
    private final ProductImageRepository productImageRepository;

    public ProductImageService(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

    public List<ProductImage> handleSaveProductImage(List<ProductImage> productImage){
        List<ProductImage> savedProductImage = this.productImageRepository.saveAll(productImage);
        return savedProductImage;
    }

    @Transactional
    public void deleteProductImagesByProductId(Long productId){
        this.productImageRepository.deleteAllByLaptopModelId(productId);
    }
}
