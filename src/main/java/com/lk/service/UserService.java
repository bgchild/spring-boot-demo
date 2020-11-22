package com.lk.service;

import com.lk.mapper.UserDao;
import com.lk.mapper.UserMapper;
import com.lk.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    public static final int INT = 1000;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserDao userDao;

    public List<User> queryAll() {
        return userMapper.selectAll();
    }

    public void batchInset(){
        long l = System.currentTimeMillis();
        List<User> users = new ArrayList<>();
        for (int i = 0; i < INT; i++) {
            User user = new User();
            user.setUserName("piliang"+i);
            user.setPassword("password"+i);
            user.setName("piliang"+i);
            user.setAge(i);
            user.setSex(1);
            user.setBirthday("2020-10-10 10:10:10");
            user.setCreated("2020-10-10 10:10:10");
            user.setUpdated("2020-10-10 10:10:10");
            user.setNote("note");
            users.add(user);
        }
        userDao.batchSave(users);
        System.out.println("执行时长："+ (System.currentTimeMillis() - l)/1000+ "s");
    }

}
