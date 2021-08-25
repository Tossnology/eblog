package com.example.eblog.service.impl;

import com.example.eblog.mapper.ViewMapper;
import com.example.eblog.view.*;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ViewService {
    final
    ViewMapper viewMapper;

    public ViewService(ViewMapper viewMapper) {
        this.viewMapper = viewMapper;
    }

    public List<PostRef> getPostRef() {
        return viewMapper.selectPostRef();
    }
    public List<PostRef> getPostRefByCategory(int id) {
        return viewMapper.selectPostRefByCategory(id);
    }
    public List<PostRef> getPostRefByTimeOrder() {
        return viewMapper.selectPostRefByTimeOrder();
    }

    public List<PostRef> getPostRefByCategoryOrderByTime(int id) {
        return viewMapper.selectPostRefByCategoryOrderByTime(id);
    }

    public List<PostRef> getPostRefOrderByCommentCount() {
        return viewMapper.selectPostRefOrderByCommentCount();
    }

    public List<PostRef> getPostRefByCategoryOrderByCommentCount(int i) {
        return viewMapper.selectPostRefByCategoryOrderByCommentCount(i);
    }

    public List<CommentView> getCommentViewByUserId(Long id) {
        return viewMapper.selectCommentViewByUserId(id);
    }

    public List<CollectionView> getCollectionViewByUserId(Long id) {
        return viewMapper.selectCollectionViewByUserId(id);
    }

    public DetailView getDetailViewByPostId(int postId) {
        return viewMapper.getDetailViewByPostId(postId);
    }

    public List<DetailCommentView> getCommentViewBy(int postId) {
        return viewMapper.getCommentViewBy(postId);
    }
}
