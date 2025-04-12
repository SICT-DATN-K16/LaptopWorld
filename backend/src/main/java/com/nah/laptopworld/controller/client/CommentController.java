package com.nah.laptopworld.controller.client;

import com.nah.laptopworld.model.Comment;
import com.nah.laptopworld.dto.request.CommentRequest;
import com.nah.laptopworld.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/products/{productId}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable Long productId,
                                               @RequestBody CommentRequest commentRequest) {
        Comment comment = commentService.addComment(commentRequest.getUserName(), productId, commentRequest.getEmail(), commentRequest.getMessage(), commentRequest.getRate(), commentRequest.getParentCommentId());
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/products/{productId}/comments")
    public ResponseEntity<List<Comment>> getCommentsByProduct(@PathVariable Long productId) {
        List<Comment> comments = commentService.getCommentsByProduct(productId);
        return ResponseEntity.ok(comments);
    }
}
