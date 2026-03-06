package com.example.employment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.employment.entity.User;

public interface UserService extends IService<User> {
    User findByUsername(String username);
    boolean register(User user);
    User login(String username, String password);
}