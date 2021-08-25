package com.example.eblog.view;

import lombok.Data;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression.DateTime;

@Data
public class PostRef {
    private int postId;
    private String username;
    private String title;
    private String name;
    private int commentCount;
    private String avatar;
    private int vipLevel;
    private String created;
    private int level;
    private int status;
    private int recommend;
    private int viewCount;
    //m_post.id, username, title, name, m_post.comment_count, avatar
}
