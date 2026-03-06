package com.example.employment.mapper;

import com.example.employment.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsername(String username);
    
    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(Long id);
    
    int insert(User user);
    
    int update(User user);
    
    int deleteById(Long id);
}