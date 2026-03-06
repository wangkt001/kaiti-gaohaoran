package com.example.employment.service.impl;

import com.example.employment.entity.User;
import com.example.employment.mapper.UserMapper;
import com.example.employment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
    
    @Override
    public boolean register(User user) {
        // 检查用户名是否已存在
        if (findByUsername(user.getUsername()) != null) {
            return false;
        }
        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus("active");
        return userMapper.insert(user) > 0;
    }
    
    @Override
    public User login(String username, String password) {
        User user = findByUsername(username);
        if (user == null) {
            return null;
        }
        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }
    
    @Override
    public User getById(Long id) {
        return userMapper.findById(id);
    }
    
    @Override
    public boolean updateById(User user) {
        return userMapper.update(user) > 0;
    }
    
    @Override
    public boolean removeById(Long id) {
        return userMapper.deleteById(id) > 0;
    }
}