package com.example.eblog.view;

import lombok.Data;

@Data
public class CollectionView {
    private int postId;
    private String title;
    private String created;
    private int viewCount;
    private int commentCount;
}
