package com.example.eblog.view;

import lombok.Data;

@Data
public class DetailCommentView {
    private int commentId;
    private int userId;
    private String username;
    private int vipLevel;
    private String created;
    private String avatar;
    private String content;
    private int voteUp;
    private boolean zanok = false;
}
