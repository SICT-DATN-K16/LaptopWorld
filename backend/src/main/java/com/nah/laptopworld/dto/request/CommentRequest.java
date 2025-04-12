package com.nah.laptopworld.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {
    private String userName;
    private String email;
    private String message;
    private int rate;
    private Long parentCommentId;
}
