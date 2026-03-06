package com.example.employment.controller;

import com.example.employment.entity.User;
import com.example.employment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();
        if (userService.register(user)) {
            result.put("success", true);
            result.put("message", "注册成功");
        } else {
            result.put("success", false);
            result.put("message", "用户名已存在");
        }
        return result;
    }
    
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginData) {
        Map<String, Object> result = new HashMap<>();
        String username = loginData.get("username");
        String password = loginData.get("password");
        User user = userService.login(username, password);
        if (user != null) {
            result.put("success", true);
            result.put("user", user);
        } else {
            result.put("success", false);
            result.put("message", "用户名或密码错误");
        }
        return result;
    }
    
    @GetMapping("/info/{userId}")
    public Map<String, Object> getInfo(@PathVariable Long userId) {
        Map<String, Object> result = new HashMap<>();
        try {
            User user = userService.getById(userId);
            result.put("success", true);
            result.put("user", user);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取用户信息失败");
        }
        return result;
    }
}