package com.nah.laptopworld.controller.admin;

import com.nah.laptopworld.model.LaptopModel;
import com.nah.laptopworld.model.ProductImage;
import com.nah.laptopworld.service.ProductImageService;
import com.nah.laptopworld.service.ProductService;
import com.nah.laptopworld.service.UploadService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController("adminProductController")
@RequestMapping("/api/admin/products")
public class ProductController {

    private final ProductService productService;
    private final UploadService uploadService;
    private final ProductImageService productImageService;

    public ProductController(ProductService productService,
            UploadService uploadService,
            ProductImageService productImageService) {
        this.productService = productService;
        this.uploadService = uploadService;
        this.productImageService = productImageService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllProducts(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "brand", required = false) String brand) {

        Pageable pageable = PageRequest.of(page - 1, 10);
        Page<LaptopModel> products = this.productService.getAllProducts(name, brand, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("products", products.getContent());
        response.put("currentPage", page);
        response.put("totalPages", products.getTotalPages());
        response.put("totalItems", products.getTotalElements());
        response.put("name", name);
        response.put("brand", brand);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LaptopModel> getProductById(@PathVariable long id) {
        Optional<LaptopModel> laptopModelOptional = this.productService.fetchProductById(id);

        if (laptopModelOptional.isPresent()) {
            return ResponseEntity.ok(laptopModelOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<LaptopModel> createProduct(
            @RequestBody @Valid LaptopModel laptopModel,
            @RequestParam(value = "images", required = false) MultipartFile[] files) {

        if (files != null && files.length > 0) {
            List<String> productImages = this.uploadService.UploadFiles(files, "laptopModel");
            List<ProductImage> productImageList = new ArrayList<>();

            for (String image : productImages) {
                ProductImage productImage = new ProductImage();
                productImage.setImage(image);
                productImage.setLaptopModel(laptopModel);
                productImageList.add(productImage);
            }

            laptopModel.setProductImages(productImageList);
        }

        LaptopModel savedModel = this.productService.handleSaveProduct(laptopModel);
        return new ResponseEntity<>(savedModel, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LaptopModel> updateProduct(
            @PathVariable long id,
            @RequestBody @Valid LaptopModel laptopModel,
            @RequestParam(value = "images", required = false) MultipartFile[] files) {

        Optional<LaptopModel> optionalProduct = this.productService.fetchProductById(id);
        if (!optionalProduct.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        LaptopModel currentLaptopModel = optionalProduct.get();

        if (files != null && files.length > 0 && !files[0].isEmpty()) {
            // Delete old images
            this.productImageService.deleteProductImagesByProductId(currentLaptopModel.getId());
            currentLaptopModel.getProductImages().clear();

            // Add new images
            List<String> productImages = this.uploadService.UploadFiles(files, "laptopModel");
            List<ProductImage> productImageList = new ArrayList<>();
            for (String image : productImages) {
                ProductImage productImage = new ProductImage();
                productImage.setImage(image);
                productImage.setLaptopModel(currentLaptopModel);
                productImageList.add(productImage);
            }
            currentLaptopModel.setProductImages(productImageList);
            this.productImageService.handleSaveProductImage(productImageList);
        }

        // Update other fields
        currentLaptopModel.setName(laptopModel.getName());
        currentLaptopModel.setDetailDesc(laptopModel.getDetailDesc());
        currentLaptopModel.setShortDesc(laptopModel.getShortDesc());
        currentLaptopModel.setTarget(laptopModel.getTarget());
        currentLaptopModel.setStatus(laptopModel.isStatus());
        currentLaptopModel.setBrand(laptopModel.getBrand());

        // Save and return
        LaptopModel updatedModel = this.productService.handleSaveProduct(currentLaptopModel);
        return ResponseEntity.ok(updatedModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id) {
        Optional<LaptopModel> optionalProduct = this.productService.fetchProductById(id);
        if (!optionalProduct.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        LaptopModel currentLaptopModel = optionalProduct.get();
        this.productImageService.deleteProductImagesByProductId(currentLaptopModel.getId());
        this.productService.deleteProductById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<LaptopModel>> searchProducts(@RequestParam("keyword") String keyword) {
        List<LaptopModel> laptopModels = this.productService.searchProduct(keyword);
        return ResponseEntity.ok(laptopModels);
    }

    @GetMapping("/count-by-brand")
    public ResponseEntity<List<Map<String, Object>>> getProductCountByBrand() {
        List<Map<String, Object>> counts = this.productService.countProductsByBrand();
        return ResponseEntity.ok(counts);
    }
}
