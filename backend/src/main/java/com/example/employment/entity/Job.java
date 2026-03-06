package com.example.employment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("job")
public class Job {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long companyId;
    private String jobTitle;
    private BigDecimal salaryMin;
    private BigDecimal salaryMax;
    private String location;
    private String education;
    private String experience;
    private String industry;
    private String description;
    private String requirements;
    private String status; // active, expired
    private LocalDateTime publishTime;
    private LocalDateTime updateTime;
}