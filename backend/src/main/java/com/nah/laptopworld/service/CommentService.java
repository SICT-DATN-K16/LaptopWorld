package com.nah.laptopworld.service;

import com.nah.laptopworld.model.Comment;
import com.nah.laptopworld.model.LaptopModel;
import com.nah.laptopworld.repository.CommentRepository;
import com.nah.laptopworld.repository.LaptopModelRepository;
import com.nah.laptopworld.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final LaptopModelRepository laptopModelRepository;
    private final UserRepository userRepository;

    public Comment addComment(String username, Long productId, String email, String message, int rate, Long parentCommentId) {
        LaptopModel laptopModel = laptopModelRepository.findFirstById(productId);
        Comment comment = new Comment();
        comment.setMessage(message);
        comment.setUserName(username);
        comment.setEmail(email);
        comment.setLaptopModel(laptopModel);
        comment.setRate(rate);

        if (parentCommentId != null) {
            Comment parentComment = commentRepository.findById(parentCommentId).orElse(null);
            comment.setParentComment(parentComment);
        }

        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByProduct(Long productId) {
        LaptopModel laptopModel = laptopModelRepository.findFirstById(productId);
        return commentRepository.findAllByLaptopModel(laptopModel);
    }

    public List<String> getAllMessageByProductId(Long productId) {
        return commentRepository.findAllMessageByProductId(productId);
    }

    public List<Comment> getAllByProductId(Long productId) {
        return commentRepository.findAllByProductId(productId);
    }
}
