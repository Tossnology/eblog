package com.example.eblog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.eblog.domain.MComment;
import com.example.eblog.domain.MUser;
import com.example.eblog.domain.MUserAction;
import com.example.eblog.service.impl.MCommentServiceImpl;
import com.example.eblog.service.impl.MUserActionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class DetailController {
    final MCommentServiceImpl commentService;
    final MUserActionServiceImpl actionService;

    public DetailController(MCommentServiceImpl commentService, MUserActionServiceImpl actionService) {
        this.commentService = commentService;
        this.actionService = actionService;
    }

    @PostMapping("/zan")
    @ResponseBody
    public Map<String, Object> zan(HttpSession session, @RequestParam(value = "ok") boolean ok, @RequestParam(value = "id") Long commentId, @RequestParam(value = "postId") Long postId) {
        Map<String, Object> res = new HashMap<>();

        if (session.getAttribute("userLogin") == null) {
            res.put("status", -1);
            res.put("msg", "点赞请先登录");
            return res;
        }

        if (!ok) {
            //点赞
            commentService.increaseOneZan(commentId);
            MUserAction clickZan = new MUserAction();
            clickZan.setUserId(((MUser)session.getAttribute("userLogin")).getId());
            clickZan.setAction(1);
            clickZan.setPostId(postId);
            clickZan.setCommentId(commentId);
            actionService.save(clickZan);
        } else {
            //取消赞
            commentService.decreaseOneZan(commentId);
            MUserAction clickZan = new MUserAction();
            clickZan.setUserId(((MUser)session.getAttribute("userLogin")).getId());
            clickZan.setAction(1);
            clickZan.setPostId(postId);
            clickZan.setCommentId(commentId);
            actionService.remove(new QueryWrapper<>(clickZan));
        }

        res.put("status", 0);

        return res;
    }

    @PostMapping("/reply")
    @ResponseBody
    public Map<String, Object> reply(HttpSession session, @RequestParam(value = "content") String content, @RequestParam(value = "postId") Long postId) {
        Map<String, Object> map = new HashMap<>();

        if (session.getAttribute("userLogin") == null) {
            map.put("msg", "评论请先登录");
            return map;
        }

        MComment comment = new MComment();
        comment.setContent(content);
        comment.setPostId(postId);
        comment.setUserId(((MUser) session.getAttribute("userLogin")).getId());
        commentService.save(comment);
        map.put("msg", "发表成功，即将刷新页面");
        map.put("postId", postId);
        return map;
    }
}
