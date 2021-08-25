package com.example.eblog.controller;

import com.example.eblog.domain.MPost;
import com.example.eblog.domain.MUser;
import com.example.eblog.service.impl.MPostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PostController {
    final MPostServiceImpl postService;

    public PostController(MPostServiceImpl postService) {
        this.postService = postService;
    }

    @PostMapping("/addPost")
    @ResponseBody
    public Map<String, String> addPost(HttpServletRequest request, @RequestParam(value = "category") String category, @RequestParam(value = "title") String title, @RequestParam(value = "content") String content) {
        MPost post = new MPost();
        post.setTitle(title);
        post.setContent(content);
        post.setCategoryId(Long.valueOf(category));
        MUser user = (MUser) request.getSession().getAttribute("userLogin");
        post.setUserId(user.getId());
        postService.save(post);
        Map<String, String> map = new HashMap<>();
        map.put("msg", "发帖成功，即将返回首页");
        return map;
    }
}
