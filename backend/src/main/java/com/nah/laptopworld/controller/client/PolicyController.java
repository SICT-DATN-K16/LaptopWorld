package com.nah.laptopworld.controller.client;

import com.nah.laptopworld.model.Policy;
import com.nah.laptopworld.service.PolicyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController("clientPolicyController") // Đặt tên bean rõ ràng
@RequestMapping("/api/policies")
public class PolicyController {

    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    // Endpoint để lấy tất cả policy (hoặc chỉ title/slug nếu cần tối ưu)
    @GetMapping
    public ResponseEntity<List<Policy>> getAllPolicies() {
        List<Policy> policies = policyService.getAllPolicies();
        // Có thể tạo DTO để chỉ trả về title và slug nếu cần
        return ResponseEntity.ok(policies);
    }

    // Endpoint để lấy chi tiết một policy dựa trên slug
    @GetMapping("/{slug}")
    public ResponseEntity<Policy> getPolicyBySlug(@PathVariable String slug) {
        Optional<Policy> policy = policyService.getPolicyBySlug(slug);
        return policy.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
} 