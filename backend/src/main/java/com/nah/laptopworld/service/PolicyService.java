package com.nah.laptopworld.service;

import com.nah.laptopworld.model.Policy;

import java.util.List;
import java.util.Optional;

public interface PolicyService {
    List<Policy> getAllPolicies();
    Optional<Policy> getPolicyById(Long id);
    Optional<Policy> getPolicyBySlug(String slug);
    Policy savePolicy(Policy policy);
    void deletePolicy(Long id);
} 