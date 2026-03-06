package com.example.employment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.employment.entity.Job;
import java.util.List;
import java.util.Map;

public interface JobService extends IService<Job> {
    List<Job> getJobs(int page, int size, Map<String, Object> filters);
    Job getJobById(Long id);
    boolean createJob(Job job);
    boolean updateJob(Long id, Job job);
    boolean deleteJob(Long id);
    List<Job> searchJobs(String keyword, Map<String, Object> filters);
}