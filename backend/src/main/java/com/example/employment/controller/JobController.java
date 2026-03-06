package com.example.employment.controller;

import com.example.employment.entity.Job;
import com.example.employment.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/jobs")
public class JobController {
    
    @Autowired
    private JobService jobService;
    
    @GetMapping
    public Map<String, Object> getJobs(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String industry,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String education,
            @RequestParam(required = false) String experience) {
        Map<String, Object> filters = new HashMap<>();
        if (industry != null) filters.put("industry", industry);
        if (location != null) filters.put("location", location);
        if (education != null) filters.put("education", education);
        if (experience != null) filters.put("experience", experience);
        
        List<Job> jobs = jobService.getJobs(page, size, filters);
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("data", jobs);
        return result;
    }
    
    @GetMapping("/{id}")
    public Map<String, Object> getJob(@PathVariable Long id) {
        Job job = jobService.getJobById(id);
        Map<String, Object> result = new HashMap<>();
        if (job != null) {
            result.put("success", true);
            result.put("data", job);
        } else {
            result.put("success", false);
            result.put("message", "岗位不存在");
        }
        return result;
    }
    
    @PostMapping
    public Map<String, Object> createJob(@RequestBody Job job) {
        Map<String, Object> result = new HashMap<>();
        if (jobService.createJob(job)) {
            result.put("success", true);
            result.put("message", "岗位创建成功");
        } else {
            result.put("success", false);
            result.put("message", "岗位创建失败");
        }
        return result;
    }
    
    @PutMapping("/{id}")
    public Map<String, Object> updateJob(@PathVariable Long id, @RequestBody Job job) {
        Map<String, Object> result = new HashMap<>();
        if (jobService.updateJob(id, job)) {
            result.put("success", true);
            result.put("message", "岗位更新成功");
        } else {
            result.put("success", false);
            result.put("message", "岗位更新失败");
        }
        return result;
    }
    
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteJob(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        if (jobService.deleteJob(id)) {
            result.put("success", true);
            result.put("message", "岗位删除成功");
        } else {
            result.put("success", false);
            result.put("message", "岗位删除失败");
        }
        return result;
    }
    
    @GetMapping("/search")
    public Map<String, Object> searchJobs(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String industry,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String education,
            @RequestParam(required = false) String experience) {
        Map<String, Object> filters = new HashMap<>();
        if (industry != null) filters.put("industry", industry);
        if (location != null) filters.put("location", location);
        if (education != null) filters.put("education", education);
        if (experience != null) filters.put("experience", experience);
        
        List<Job> jobs = jobService.searchJobs(keyword, filters);
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("data", jobs);
        return result;
    }
}