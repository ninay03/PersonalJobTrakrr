package com.ninay.JobTrakrr.controller;
import com.ninay.JobTrakrr.model.ApplicationStatus;
import com.ninay.JobTrakrr.model.JobApplication;
import com.ninay.JobTrakrr.service.JobApplicationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    public JobApplicationController(JobApplicationService jobApplicationService){
        this.jobApplicationService = jobApplicationService;
    }
    @PostMapping("/application")
    public JobApplication createApplicaton(@RequestBody JobApplication application){
        return jobApplicationService.createApplication(application);
    }
    @GetMapping("/application")
    public List<JobApplication> getAllApplicaton(){
        return jobApplicationService.getAllApplications();
    }
    @GetMapping("/application/{id}")
    public Optional <JobApplication> getApplicationById(@PathVariable Long id){
        return jobApplicationService.getApplicationById(id);
    }
    @PutMapping("/application/{id}")
    public JobApplication updateApplication(
            @PathVariable Long id,@RequestBody JobApplication application ){
        return jobApplicationService.updateApplication(id, application);
    }
    @DeleteMapping("/application/{id}")
    public void deleteApplication(@PathVariable Long id){
        jobApplicationService.deleteApplication(id);
    }
    @PatchMapping("/application/{id}")
    public JobApplication updateStatus(@PathVariable Long id, @RequestBody ApplicationStatus newStatus){
        return jobApplicationService.updateStatus(id, newStatus);
    }
}
