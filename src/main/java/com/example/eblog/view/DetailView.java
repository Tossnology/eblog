package com.example.eblog.view;

import lombok.Data;

@Data
public class DetailView {
    private int postId;
    private int userId;
    private String title;//标题
    private String name;//分类名字
    private int status;//状态（未结/已结）
    private int recommend;//精
    private int level;//置顶等级
    private int viewCount;
    private int commentCount;
    private String content;
    private String username;
    private String avatar;
    private int vipLevel;
    private String created;
}
