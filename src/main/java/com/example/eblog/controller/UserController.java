package com.example.eblog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.eblog.domain.MPost;
import com.example.eblog.domain.MUser;
import com.example.eblog.service.impl.*;
import com.example.eblog.view.CollectionView;
import com.example.eblog.view.CommentView;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class UserController {
    final MPostServiceImpl postService;
    final MCommentServiceImpl commentService;
    final ViewService viewService;
    final MUserServiceImpl userService;

    public UserController(MPostServiceImpl postService, MCommentServiceImpl commentService, ViewService viewService, MUserServiceImpl userService) {
        this.postService = postService;
        this.commentService = commentService;
        this.viewService = viewService;
        this.userService = userService;
    }

    @GetMapping("/myPost")
    public String myPost(HttpSession session, Model model) {
        MUser user = (MUser) session.getAttribute("userLogin");
        Long id = user.getId();
        MPost post = new MPost();
        post.setUserId(id);
        List<MPost> myPosts = postService.list(new QueryWrapper<>(post));
        List<CollectionView> collectionViews = viewService.getCollectionViewByUserId(id);
        model.addAttribute("myPosts", myPosts);
        model.addAttribute("collections", collectionViews);
        return "user/index";
    }

    @GetMapping("/myCollection")
    public String myCollection() {
        return "user/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.setAttribute("userLogin", null);
        return "redirect:/index";
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        MUser user = (MUser) session.getAttribute("userLogin");
        MPost post = new MPost();
        post.setUserId(user.getId());
        List<MPost> posts = postService.list(new QueryWrapper<>(post));
        List<CommentView> comments = viewService.getCommentViewByUserId(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("posts", posts);
        model.addAttribute("comments", comments);
        return "/user/home";
    }

    @GetMapping("/set")
    public String set() {
        return "/user/set";
    }

    @PostMapping("/setCheck")
    @ResponseBody
    public Map<String, String> setCheck(HttpSession session, @RequestParam(value = "email") String email, @RequestParam(value = "username") String username, @RequestParam(value = "sex") String gender, @RequestParam(value = "sign") String sign) {
        Map<String, String> map = new HashMap<>();
        MUser user = new MUser();
        Long id = ((MUser) session.getAttribute("userLogin")).getId();
        user.setEmail(email);
        MUser result = userService.getOne(new QueryWrapper<>(user));
        if (result != null && !result.getId().equals(id)) {
            map.put("msg", "该邮箱已被注册");
            return map;
        }
        user.setEmail(null);
        user.setUsername(username);
        result = userService.getOne(new QueryWrapper<>(user));
        if (result != null && !result.getId().equals(id)) {
            map.put("msg", "用户名已存在");
            return map;
        }
        user.setEmail(email);
        user.setGender(gender);
        user.setSign(sign);
        user.setId(id);
        userService.updateById(user);
        ((MUser) session.getAttribute("userLogin")).setEmail(email);
        ((MUser) session.getAttribute("userLogin")).setUsername(username);
        ((MUser) session.getAttribute("userLogin")).setGender(gender);
        ((MUser) session.getAttribute("userLogin")).setSign(sign);
        map.put("msg", "修改成功");
        return map;
    }

    @SneakyThrows
    @PostMapping("/uploadAvatar")
    @ResponseBody
    public Map<String, Object> uploadAvatar(HttpSession session, @RequestPart(value = "file") MultipartFile avatar) {
        Map<String, Object> map = new HashMap<>();
        if (!avatar.isEmpty()) {
            String fileName = avatar.getOriginalFilename();
            int begin = Objects.requireNonNull(avatar.getOriginalFilename()).lastIndexOf(".");
            int last = avatar.getOriginalFilename().length();
            String type = fileName.substring(begin, last);
            avatar.transferTo(new File("F:/avatars/" + ((MUser) session.getAttribute("userLogin")).getUsername() + type));

            map.put("msg", "上传成功");
            map.put("status", 0);
            map.put("url", "/avatars/" + ((MUser) session.getAttribute("userLogin")).getUsername() + type);
            return map;
        }
        map.put("msg", "文件不能为空");
        return map;
    }

    @PostMapping("/setAvatar")
    @ResponseBody
    public Map<String, Object> setAvatar(HttpSession session, @RequestParam(value = "avatar") String avatar) {
        MUser user = new MUser();
        user.setId(((MUser) session.getAttribute("userLogin")).getId());
        user.setAvatar(avatar);
        userService.updateById(user);
        ((MUser) session.getAttribute("userLogin")).setAvatar(avatar);
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "上传成功");
        return map;
    }

    @PostMapping("/modifyPassword")
    @ResponseBody
    public Map<String, Object> modifyPassword(HttpSession session, @RequestParam(value = "nowpass") String nowpass, @RequestParam(value = "pass") String pass, @RequestParam(value = "repass") String repass) {
        Map<String, Object> map = new HashMap<>();
        if (!pass.equals(repass)) {
            map.put("msg", "新密码两次输入不一致");
            return map;
        }

        MUser user = new MUser();
        Long id = ((MUser) session.getAttribute("userLogin")).getId();
        user.setId(id);
        user.setPassword(nowpass);
        MUser result = userService.getOne(new QueryWrapper<>(user));
        if (result == null) {
            map.put("msg", "原密码错误");
            return map;
        }

        user.setPassword(pass);
        userService.updateById(user);
        map.put("msg", "修改成功，需重新登录");
        session.setAttribute("userLogin", null);
        return map;
    }

    @GetMapping("/message")
    public String message() {
        return "user/message";
    }

    @GetMapping("/homeComment")
    public String home(Model model, @RequestParam(value = "userId") Long userId) {
        MUser condition = new MUser();
        condition.setId(userId);
        MPost post = new MPost();
        post.setUserId(userId);
        List<MPost> posts = postService.list(new QueryWrapper<>(post));
        List<CommentView> comments = viewService.getCommentViewByUserId(userId);
        MUser user = userService.getOne(new QueryWrapper<>(condition));
        model.addAttribute("user", user);
        model.addAttribute("posts", posts);
        model.addAttribute("comments", comments);
        return "/user/home";
    }
}
