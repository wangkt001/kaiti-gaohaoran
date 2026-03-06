package com.example.employment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("resume")
public class Resume {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long studentId;
    private String resumeName;
    private String basicInfo;
    private String education;
    private String experience;
    private String projects;
    private String skills;
    private String status; // draft, published
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}