package com.lk.controller;

import com.lk.interceptor.ParamsAopAnnotation;
import com.lk.pojo.User;
import com.lk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class HelloController {
    @Autowired
    private UserService userService;

    @ParamsAopAnnotation
    @RequestMapping("hello")
    public List<User> hello(String a,HttpServletRequest request) {
        String id = request.getParameter("id");
        System.out.println(id);
        // return userService.queryAll();
        return null;
    }
}
