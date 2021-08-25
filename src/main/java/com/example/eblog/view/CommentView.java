package com.example.eblog.view;

import lombok.Data;

@Data
public class CommentView {
    private String username;
    private int postId;
    private String title;
    private String content;
    private String created;
}
