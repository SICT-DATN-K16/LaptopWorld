package com.nah.laptopworld.controller.client;

import com.nah.laptopworld.model.Blog;
import com.nah.laptopworld.service.BlogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController("clientBlogController") // Đặt tên bean rõ ràng
@RequestMapping("/api/blogs") // Base path cho client API
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    // Lấy danh sách blog (phân trang, sắp xếp theo ngày tạo giảm dần)
    @GetMapping
    public ResponseEntity<Map<String, Object>> getPublishedBlogs(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "9") int size) { // Có thể set size mặc định khác

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Blog> blogPage = blogService.getAllBlogs(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("blogs", blogPage.getContent());
        response.put("currentPage", blogPage.getNumber() + 1);
        response.put("totalItems", blogPage.getTotalElements());
        response.put("totalPages", blogPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    // Lấy chi tiết blog theo slug
    @GetMapping("/{slug}")
    public ResponseEntity<Blog> getBlogBySlug(@PathVariable String slug) {
        Optional<Blog> blog = blogService.getBlogBySlug(slug);
        return blog.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

} 