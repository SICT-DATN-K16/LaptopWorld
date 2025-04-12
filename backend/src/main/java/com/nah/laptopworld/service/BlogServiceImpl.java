package com.nah.laptopworld.service;

import com.nah.laptopworld.model.Blog;
import com.nah.laptopworld.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;

    // Pattern để tạo slug (giống PolicyServiceImpl)
    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
    private static final Pattern EDGESDHASHES = Pattern.compile("(^-|-$)");

    @Autowired
    public BlogServiceImpl(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    // Hàm tiện ích tạo slug (giống PolicyServiceImpl)
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

    // Hàm kiểm tra và tạo slug duy nhất (giống PolicyServiceImpl, thay Policy -> Blog)
    private String createUniqueSlug(String title, Long currentBlogId) {
        String baseSlug = generateSlug(title);
        String finalSlug = baseSlug;
        int counter = 1;

        while (true) {
            Optional<Blog> existingBlog = blogRepository.findBySlug(finalSlug);
            if (existingBlog.isEmpty() || (currentBlogId != null && existingBlog.get().getId() == currentBlogId)) {
                break;
            }
            finalSlug = baseSlug + "-" + counter++;
        }
        return finalSlug;
    }

    @Override
    public Page<Blog> getAllBlogs(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }
    
    @Override
    public List<Blog> getAllBlogs() {
        return blogRepository.findAll(); // Có thể cần sắp xếp theo ngày tạo?
    }

    @Override
    public Optional<Blog> getBlogById(Long id) {
        return blogRepository.findById(id);
    }

    @Override
    public Optional<Blog> getBlogBySlug(String slug) {
        return blogRepository.findBySlug(slug);
    }

    @Override
    public Blog saveBlog(Blog blog) {
        // Logic tạo slug tự động
        Long currentId = blog.getId() != 0 ? blog.getId() : null;
        String uniqueSlug = createUniqueSlug(blog.getTitle(), currentId);
        blog.setSlug(uniqueSlug);
        
        // Cần set User cho blog trước khi lưu, có thể lấy từ SecurityContext hoặc tham số
        // Ví dụ: User currentUser = ... ; blog.setUser(currentUser);
        // Hiện tại chưa có logic set User, cần bổ sung trong Controller hoặc Service

        return blogRepository.save(blog);
    }

    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
} 