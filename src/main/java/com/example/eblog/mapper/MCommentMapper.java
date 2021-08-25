package com.example.eblog.mapper;

import com.example.eblog.domain.MComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Entity com.example.eblog.domain.MComment
 */

public interface MCommentMapper extends BaseMapper<MComment> {

    boolean increaseOneZan(Long commentId);

    boolean decreaseOneZan(Long commentId);
}




