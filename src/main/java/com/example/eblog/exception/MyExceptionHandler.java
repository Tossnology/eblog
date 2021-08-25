package com.example.eblog.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object exceptionHandler(HttpServletRequest request, Exception e) {
        System.out.println("出错啦：" + e.getMessage());
        String header = request.getHeader("X-Requested-With");
        // 判断是同步请求还是ajax请求
        if (header != null && header.equals("XMLHttpRequest")) {
            Map<String, String> map = new HashMap<>();
            map.put("msg", e.getMessage());
            return map;
        } else {
            return new ModelAndView("other/404").addObject("msg", e.getMessage());
        }
    }
}
