package com.nah.laptopworld.service;

import com.nah.laptopworld.model.Policy;
import com.nah.laptopworld.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class PolicyServiceImpl implements PolicyService {

    private final PolicyRepository policyRepository;

    // Pattern để loại bỏ dấu tiếng Việt
    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
    private static final Pattern EDGESDHASHES = Pattern.compile("(^-|-$)");

    @Autowired
    public PolicyServiceImpl(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    private String generateSlug(String input) {
        if (input == null) {
            return "";
        }
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        slug = slug.toLowerCase(Locale.ENGLISH);
        slug = EDGESDHASHES.matcher(slug).replaceAll("");
        slug = slug.replaceAll("-{2,}", "-");
        return slug;
    }

    private String createUniqueSlug(String title, Long currentPolicyId) {
        String baseSlug = generateSlug(title);
        String finalSlug = baseSlug;
        int counter = 1;

        while (true) {
            Optional<Policy> existingPolicy = policyRepository.findBySlug(finalSlug);
            if (existingPolicy.isEmpty() || (currentPolicyId != null && existingPolicy.get().getId() == currentPolicyId)) {
                break;
            }
            finalSlug = baseSlug + "-" + counter++;
        }
        return finalSlug;
    }


    @Override
    public List<Policy> getAllPolicies() {
        return policyRepository.findAll();
    }

    @Override
    public Optional<Policy> getPolicyById(Long id) {
        return policyRepository.findById(id);
    }

    @Override
    public Optional<Policy> getPolicyBySlug(String slug) {
        return policyRepository.findBySlug(slug);
    }

    @Override
    public Policy savePolicy(Policy policy) {
        // Xác định ID hiện tại (null nếu là tạo mới)
        Long currentId = policy.getId() != 0 ? policy.getId() : null;
        // Tạo slug duy nhất từ title
        String uniqueSlug = createUniqueSlug(policy.getTitle(), currentId);
        policy.setSlug(uniqueSlug);
        return policyRepository.save(policy);
    }

    @Override
    public void deletePolicy(Long id) {
        policyRepository.deleteById(id);
    }
} 