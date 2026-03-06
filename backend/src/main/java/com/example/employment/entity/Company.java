package com.example.employment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("company")
public class Company {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String companyName;
    private String industry;
    private String scale;
    private String address;
    private String contactName;
    private String contactPhone;
    private String contactEmail;
    private String logo;
    private String status; // pending, approved, rejected
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}