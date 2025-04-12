package com.nah.laptopworld.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Setter
@Getter
public class ProductCriteriaResponse {
    private Optional<String> page;
    private Optional<List<String>> brand;
    private Optional<List<String>> target;
    private Optional<List<String>> price;
    private Optional<String> sort;
}
