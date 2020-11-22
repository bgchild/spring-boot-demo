package com.lk.controller;

import com.lk.pojo.User;
import com.lk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/all")
    public String all(ModelMap model) {
        // 查询用户
        List<User> users = userService.queryAll();
        model.addAttribute("users", users);
        // 返回模板名称（就是classpath:/templates/目录下的html文件名）
        return "index";
    }

//    @GetMapping("/insert")
//    public void insert() {
//        userService.batchInset();
//    }
}
