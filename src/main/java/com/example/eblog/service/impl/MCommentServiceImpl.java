package com.example.eblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.eblog.domain.MComment;
import com.example.eblog.service.MCommentService;
import com.example.eblog.mapper.MCommentMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class MCommentServiceImpl extends ServiceImpl<MCommentMapper, MComment>
    implements MCommentService{

    public boolean increaseOneZan(Long commentId) {
        return baseMapper.increaseOneZan(commentId);
    }

    public boolean decreaseOneZan(Long commentId) {
        return baseMapper.decreaseOneZan(commentId);
    }
}




