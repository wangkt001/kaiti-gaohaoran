package com.example.employment.service;

import com.example.employment.entity.User;

public interface UserService {
    User findByUsername(String username);
    boolean register(User user);
    User login(String username, String password);
    User getById(Long id);
    boolean updateById(User user);
    boolean removeById(Long id);
}