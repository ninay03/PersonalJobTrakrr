package com.ninay.JobTrakrr.service;


import com.ninay.JobTrakrr.model.ApplicationStatus;
import com.ninay.JobTrakrr.model.JobApplication;
import com.ninay.JobTrakrr.repository.JobApplicationRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class JobApplicationService {

private final JobApplicationRepo jobApplicationRepo;

    public JobApplication createApplication(JobApplication application) {
        return jobApplicationRepo.save(application);
    }

    public JobApplicationService(JobApplicationRepo jobApplicationRepo) {
        this.jobApplicationRepo = jobApplicationRepo;
    }

    public List<JobApplication > getAllApplications(){
        return  jobApplicationRepo.findAll();
    }
    public Optional<JobApplication> getApplicationById(Long id){
        return jobApplicationRepo.findById(id);
    }

    public JobApplication updateApplication(Long id, JobApplication updatedApplication){
        JobApplication existingApplication = jobApplicationRepo.findById(id).orElseThrow();
        existingApplication.setCompany(updatedApplication.getCompany());
        existingApplication.setRole(updatedApplication.getRole());
        existingApplication.setJobUrl(updatedApplication.getJobUrl());
        existingApplication.setAppliedDate(updatedApplication.getAppliedDate());
        existingApplication.setResumeUrl(updatedApplication.getResumeUrl());
        existingApplication.setDeadline(updatedApplication.getDeadline());
        existingApplication.setNotes(updatedApplication.getNotes());

        return jobApplicationRepo.save(existingApplication);
    }

    public void deleteApplication(Long id){
        if (!jobApplicationRepo.existsById(id)) {
            throw new RuntimeException("Application not found");
        }
        jobApplicationRepo.deleteById(id);
    }
    public JobApplication updateStatus(Long id, ApplicationStatus newStatus){
        JobApplication application = jobApplicationRepo.findById(id).orElseThrow();
        ApplicationStatus currentStatus = application.getStatus();
            if (!isValidTransition(currentStatus,newStatus)){
                throw new RuntimeException("Invalid status transition");
            }
            application.setStatus(newStatus);
        if (newStatus == ApplicationStatus.APPLIED){
            application.setAppliedDate(LocalDate.now());
        }

        return jobApplicationRepo.save(application);
    }

    private boolean isValidTransition(
            ApplicationStatus currentStatus,
            ApplicationStatus newStatus) {


        if (currentStatus == ApplicationStatus.SAVED &&
        newStatus == ApplicationStatus.APPLIED) {
            return true;
        }
            if (currentStatus == ApplicationStatus.APPLIED &&
                    newStatus == ApplicationStatus.SCREENING) {
                return true;
            }
            if (currentStatus == ApplicationStatus.INTERVIEW &&
                    newStatus == ApplicationStatus.OFFER){
                return true;
            }
            if (currentStatus == ApplicationStatus.APPLIED &&
                    newStatus == ApplicationStatus.REJECTED){
                return true;
            }
            if(currentStatus == ApplicationStatus.SCREENING &&
                    newStatus == ApplicationStatus.REJECTED){
                return true;
            }
            if (currentStatus == ApplicationStatus.SAVED &&
                    newStatus == ApplicationStatus.WITHDRAWN){
                return true;
            }
            if (currentStatus == ApplicationStatus.SCREENING &&
                    newStatus == ApplicationStatus.WITHDRAWN){
                return true;
            }
            if (currentStatus == ApplicationStatus.INTERVIEW &&
                    newStatus == ApplicationStatus.WITHDRAWN){
                return true;
            }
            if (currentStatus == ApplicationStatus.APPLIED &&
                    newStatus == ApplicationStatus.WITHDRAWN){
                return true;
            }

        return currentStatus == ApplicationStatus.SCREENING &&
                newStatus == ApplicationStatus.INTERVIEW;
    }
}
