package com.example.eblog.mapper;

import com.example.eblog.view.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ViewMapper {
    List<PostRef> selectPostRef();
    List<PostRef> selectPostRefByCategory(int id);
    List<PostRef> selectPostRefByTimeOrder();

    List<PostRef> selectPostRefByCategoryOrderByTime(int id);

    List<PostRef> selectPostRefOrderByCommentCount();

    List<PostRef> selectPostRefByCategoryOrderByCommentCount(int i);

    List<CommentView> selectCommentViewByUserId(Long id);

    List<CollectionView> selectCollectionViewByUserId(Long id);

    DetailView getDetailViewByPostId(int postId);

    List<DetailCommentView> getCommentViewBy(int postId);
}
