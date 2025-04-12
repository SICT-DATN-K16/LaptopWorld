package com.nah.laptopworld.service;

import com.nah.laptopworld.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BlogService {
    Page<Blog> getAllBlogs(Pageable pageable);
    List<Blog> getAllBlogs(); // Lấy tất cả không phân trang (có thể cần cho client?)
    Optional<Blog> getBlogById(Long id);
    Optional<Blog> getBlogBySlug(String slug);
    Blog saveBlog(Blog blog);
    void deleteBlog(Long id);
} 