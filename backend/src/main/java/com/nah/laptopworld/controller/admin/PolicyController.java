package com.nah.laptopworld.controller.admin;

import com.nah.laptopworld.model.Policy;
import com.nah.laptopworld.service.PolicyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController("adminPolicyController") // Đặt tên bean rõ ràng
@RequestMapping("/api/admin/policies")
public class PolicyController {

    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @GetMapping
    public ResponseEntity<List<Policy>> getAllPolicies() {
        List<Policy> policies = policyService.getAllPolicies();
        return ResponseEntity.ok(policies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Policy> getPolicyById(@PathVariable Long id) {
        Optional<Policy> policy = policyService.getPolicyById(id);
        return policy.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Policy> createPolicy(@Valid @RequestBody Policy policy) {
        // Kiểm tra slug tồn tại trước khi tạo nếu cần
        Optional<Policy> existingPolicy = policyService.getPolicyBySlug(policy.getSlug());
        if (existingPolicy.isPresent()) {
            // Có thể trả về lỗi hoặc xử lý khác
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 Conflict
        }
        Policy savedPolicy = policyService.savePolicy(policy);
        return new ResponseEntity<>(savedPolicy, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Policy> updatePolicy(@PathVariable Long id, @Valid @RequestBody Policy policyDetails) {
        Optional<Policy> optionalPolicy = policyService.getPolicyById(id);
        if (optionalPolicy.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Policy existingPolicy = optionalPolicy.get();
        // Kiểm tra slug mới có bị trùng với policy khác không (ngoại trừ chính nó)
        Optional<Policy> policyWithNewSlug = policyService.getPolicyBySlug(policyDetails.getSlug());
        if (policyWithNewSlug.isPresent() && policyWithNewSlug.get().getId() != id) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 Conflict
        }

        existingPolicy.setTitle(policyDetails.getTitle());
        existingPolicy.setSlug(policyDetails.getSlug());
        existingPolicy.setContent(policyDetails.getContent());
        // createdAt không nên được cập nhật

        Policy updatedPolicy = policyService.savePolicy(existingPolicy);
        return ResponseEntity.ok(updatedPolicy);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePolicy(@PathVariable Long id) {
        if (policyService.getPolicyById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        policyService.deletePolicy(id);
        return ResponseEntity.noContent().build();
    }
}