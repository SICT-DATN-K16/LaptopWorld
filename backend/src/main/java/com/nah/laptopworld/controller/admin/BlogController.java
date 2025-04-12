package com.nah.laptopworld.controller.admin;

import com.nah.laptopworld.model.Blog;
import com.nah.laptopworld.model.User;
import com.nah.laptopworld.service.BlogService;
import com.nah.laptopworld.service.UploadService;
import com.nah.laptopworld.service.UserService; // Để lấy user tạo blog
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController("adminBlogController")
@RequestMapping("/api/admin/blogs")
public class BlogController {

    private final BlogService blogService;
    private final UserService userService; // Inject UserService
    private final UploadService uploadService; // Inject UploadService nếu cần xử lý ảnh thumbnail

    public BlogController(BlogService blogService, UserService userService, UploadService uploadService) {
        this.blogService = blogService;
        this.userService = userService;
        this.uploadService = uploadService;
    }

    // Lấy danh sách blog (phân trang)
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllBlogs(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String[] sort) { // Thêm sắp xếp

        Sort.Direction direction = sort[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(direction, sort[0]));

        Page<Blog> blogPage = blogService.getAllBlogs(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("blogs", blogPage.getContent());
        response.put("currentPage", blogPage.getNumber() + 1);
        response.put("totalItems", blogPage.getTotalElements());
        response.put("totalPages", blogPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    // Lấy chi tiết blog theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlogById(@PathVariable Long id) {
        Optional<Blog> blog = blogService.getBlogById(id);
        return blog.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Tạo blog mới
    @PostMapping
    public ResponseEntity<Blog> createBlog(
            @Valid @RequestPart("blog") Blog blog, // Nhận Blog JSON
            @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnailFile) { // Nhận file ảnh

        // 1. Lấy thông tin User đang đăng nhập
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = "";
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            currentUsername = ((UserDetails) authentication.getPrincipal()).getUsername();
        } else if (authentication != null) {
            currentUsername = authentication.getName(); // Có thể là email hoặc username
        }

        User currentUser = userService.getUserByEmail(currentUsername); // Giả sử username là email
        if (currentUser == null) {
             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Hoặc lỗi khác
        }
        blog.setUser(currentUser); // Gán user cho blog

        // 2. Xử lý upload ảnh thumbnail nếu có
        if (thumbnailFile != null && !thumbnailFile.isEmpty()) {
            try {
                String thumbnailUrl = uploadService.handleSaveUploadFile(thumbnailFile, "blog_thumbnails");
                blog.setThumbnailUrl(thumbnailUrl);
            } catch (Exception e) {
                // Log lỗi upload và có thể trả về lỗi
                System.err.println("Lỗi upload thumbnail: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        // 3. Lưu blog (đã bao gồm tạo slug trong service)
        Blog savedBlog = blogService.saveBlog(blog);
        return new ResponseEntity<>(savedBlog, HttpStatus.CREATED);
    }

    // Cập nhật blog
    @PutMapping("/{id}")
    public ResponseEntity<Blog> updateBlog(
            @PathVariable Long id,
            @Valid @RequestPart("blog") Blog blogDetails,
            @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnailFile) {

        Optional<Blog> optionalBlog = blogService.getBlogById(id);
        if (optionalBlog.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Blog existingBlog = optionalBlog.get();

        // Cập nhật các trường
        existingBlog.setTitle(blogDetails.getTitle());
        existingBlog.setContent(blogDetails.getContent());
        // Slug sẽ tự động cập nhật trong saveBlog nếu title thay đổi

        // Xử lý cập nhật ảnh thumbnail nếu có file mới
        if (thumbnailFile != null && !thumbnailFile.isEmpty()) {
             try {
                // Cân nhắc xóa ảnh cũ trước khi upload ảnh mới
                // uploadService.deleteFile(existingBlog.getThumbnailUrl()); // Cần implement hàm xóa file
                String newThumbnailUrl = uploadService.handleSaveUploadFile(thumbnailFile, "blog_thumbnails");
                existingBlog.setThumbnailUrl(newThumbnailUrl);
            } catch (Exception e) {
                System.err.println("Lỗi upload thumbnail khi cập nhật: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        // User tạo blog không nên thay đổi khi cập nhật

        Blog updatedBlog = blogService.saveBlog(existingBlog);
        return ResponseEntity.ok(updatedBlog);
    }

    // Xóa blog
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable Long id) {
        if (blogService.getBlogById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        // Cân nhắc xóa ảnh thumbnail liên quan trước khi xóa blog
        // Optional<Blog> blog = blogService.getBlogById(id);
        // if (blog.isPresent() && blog.get().getThumbnailUrl() != null) { ... xóa file ... }
        blogService.deleteBlog(id);
        return ResponseEntity.noContent().build();
    }
} 