package com.nah.laptopworld.service.specification;

import com.nah.laptopworld.model.LaptopModel;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ProductSpecs {
    public static Specification<LaptopModel> nameLike(String name){
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.like(root.get("name"), "%"+name+"%");
    }

    public static Specification<LaptopModel> minPrice(double minPrice){
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.ge(root.join("laptopVariant").get("price"), minPrice);
    }

    public static Specification<LaptopModel> maxPrice(double maxPrice){
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.le(root.join("laptopVariant").get("price"), maxPrice);
    }

    public static Specification<LaptopModel> matchBrand(String brandName){
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("brand").get("name"), brandName);
    }

    public static Specification<LaptopModel> matchListBrand(List<String> brandNames){
        return (root, query, criteriaBuilder) -> {
            Join<Object, Object> brandJoin = root.join("brand");
            return brandJoin.get("name").in(brandNames);
        };
    }

    public static Specification<LaptopModel> matchListTarget(List<String> target){
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.in(root.get("target")).value(target);
    }

    public static Specification<LaptopModel> matchPrice(double min, double max){
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.and(
                        criteriaBuilder.ge(root.join("laptopVariant").get("price"), min),
                        criteriaBuilder.le(root.join("laptopVariant").get("price"), max));
    }

    public static Specification<LaptopModel> matchMultiplePrice(double min, double max){
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.between(root.join("laptopVariant").get("price"), min, max);
    }
}
