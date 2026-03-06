package com.example.employment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.employment.entity.Job;
import com.example.employment.mapper.JobMapper;
import com.example.employment.service.JobService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements JobService {
    
    @Override
    public List<Job> getJobs(int page, int size, Map<String, Object> filters) {
        QueryWrapper<Job> queryWrapper = new QueryWrapper<>();
        
        // 应用筛选条件
        if (filters != null) {
            if (filters.containsKey("industry")) {
                queryWrapper.eq("industry", filters.get("industry"));
            }
            if (filters.containsKey("location")) {
                queryWrapper.eq("location", filters.get("location"));
            }
            if (filters.containsKey("education")) {
                queryWrapper.eq("education", filters.get("education"));
            }
            if (filters.containsKey("experience")) {
                queryWrapper.eq("experience", filters.get("experience"));
            }
        }
        
        // 只查询激活状态的岗位
        queryWrapper.eq("status", "active");
        
        // 按发布时间倒序
        queryWrapper.orderByDesc("publish_time");
        
        // 分页
        int offset = (page - 1) * size;
        return baseMapper.selectList(queryWrapper.last("LIMIT " + offset + ", " + size));
    }
    
    @Override
    public Job getJobById(Long id) {
        return baseMapper.selectById(id);
    }
    
    @Override
    public boolean createJob(Job job) {
        job.setStatus("active");
        return save(job);
    }
    
    @Override
    public boolean updateJob(Long id, Job job) {
        job.setId(id);
        return updateById(job);
    }
    
    @Override
    public boolean deleteJob(Long id) {
        return removeById(id);
    }
    
    @Override
    public List<Job> searchJobs(String keyword, Map<String, Object> filters) {
        QueryWrapper<Job> queryWrapper = new QueryWrapper<>();
        
        // 关键词搜索
        if (keyword != null && !keyword.isEmpty()) {
            queryWrapper.like("job_title", keyword)
                    .or().like("description", keyword)
                    .or().like("requirements", keyword);
        }
        
        // 应用筛选条件
        if (filters != null) {
            if (filters.containsKey("industry")) {
                queryWrapper.eq("industry", filters.get("industry"));
            }
            if (filters.containsKey("location")) {
                queryWrapper.eq("location", filters.get("location"));
            }
            if (filters.containsKey("education")) {
                queryWrapper.eq("education", filters.get("education"));
            }
            if (filters.containsKey("experience")) {
                queryWrapper.eq("experience", filters.get("experience"));
            }
        }
        
        // 只查询激活状态的岗位
        queryWrapper.eq("status", "active");
        
        // 按发布时间倒序
        queryWrapper.orderByDesc("publish_time");
        
        return baseMapper.selectList(queryWrapper);
    }
}