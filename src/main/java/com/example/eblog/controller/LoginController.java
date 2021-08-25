package com.example.eblog.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.eblog.domain.MUser;
import com.example.eblog.service.impl.MUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    final MUserServiceImpl userService;

    final JavaMailSender javaMailSender;

    public LoginController(MUserServiceImpl userService, JavaMailSender javaMailSender) {
        this.userService = userService;
        this.javaMailSender = javaMailSender;
    }

    @PostMapping("/loginCheck")
    public String loginCheck(Model model, HttpSession session, @RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {

        MUser user = new MUser();
        user.setUsername(username);
        user.setPassword(password);
        Wrapper<MUser> wrapper = new QueryWrapper<>(user);
        MUser result = userService.getOne(wrapper);

        if(result == null) {
            user.setUsername(null);
            user.setEmail(username);
            result = userService.getOne(wrapper);
        }

        if (result != null) {
            session.setAttribute("userLogin", result);
            System.out.println(result.getSign());
            return "redirect:/index";
        } else {
            model.addAttribute("msg", "用户名或密码错误");
            return "/user/login";
        }
    }

    @GetMapping("/register")
    public String register() {
        return "user/reg";
    }

    @PostMapping("/registerCheck")
    @ResponseBody
    public Map<String, String> registerCheck(@RequestParam(value = "email") String email, @RequestParam(value = "username") String username, @RequestParam(value = "password") String password, @RequestParam(value = "repassword") String repassword) {
        Map<String, String> result = new HashMap<>();
        if (!password.equals(repassword)) {
            System.out.println("两次密码不一致");
            result.put("msg", "两次密码不一致");
            return result;
        }

        MUser user = new MUser();
        user.setEmail(email);
        if (userService.getOne(new QueryWrapper<>(user)) != null) {
            result.put("msg", "该邮箱已被注册");
            return result;
        }

        user.setUsername(username);
        if (userService.getOne(new QueryWrapper<>(user)) != null) {
            result.put("msg", "用户名已存在");
            return result;
        }

        user.setPassword(password);
        userService.save(user);
        result.put("msg", "注册成功，即将返回登录页");
        return result;
    }

    @GetMapping("/forget")
    public String forget() {
        return "/user/forget";
    }

    @PostMapping("/sendEmail")
    @ResponseBody
    public Map<String, String> sendEmail(@RequestParam(value = "email") String email) {
        Map<String, String> result = new HashMap<>();
        MUser user = new MUser();
        user.setEmail(email);
        MUser resultUser = userService.getOne(new QueryWrapper<>(user));
        if (resultUser == null) {
            result.put("msg", "无此用户");
            return result;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("博客网找回密码");
        message.setFrom("462272701@qq.com");
        message.setTo(email);
        message.setText("您的密码为：" + resultUser.getPassword());
        javaMailSender.send(message);
        result.put("msg", "已发送密码至" + email + "请注意查收，即将返回登录页");
        return result;
    }
}
