package com.ninay.JobTrakrr.service;

import com.ninay.JobTrakrr.dto.JobApplicationRequest;
import com.ninay.JobTrakrr.dto.JobApplicationResponse;
import com.ninay.JobTrakrr.exception.ApplicationNotFound;
import com.ninay.JobTrakrr.model.ApplicationStatus;
import com.ninay.JobTrakrr.model.JobApplication;
import com.ninay.JobTrakrr.repository.JobApplicationRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.ninay.JobTrakrr.dto.DashboardStatisticsResponse;
import java.time.LocalDate;
import java.util.List;

@Service
public class JobApplicationService {

    private final JobApplicationRepo jobApplicationRepo;

    public JobApplicationService(JobApplicationRepo jobApplicationRepo) {
        this.jobApplicationRepo = jobApplicationRepo;
    }

    // CREATE
    public JobApplicationResponse createApplication(JobApplicationRequest request) {

        JobApplication application = new JobApplication();

        application.setCompany(request.getCompany());
        application.setRole(request.getRole());
        application.setJobUrl(request.getJobUrl());
        application.setStatus(request.getStatus());
        application.setAppliedDate(request.getAppliedDate());
        application.setResumeUrl(request.getResumeUrl());
        application.setDeadline(request.getDeadline());
        application.setNotes(request.getNotes());

        JobApplication savedApplication = jobApplicationRepo.save(application);

        return mapToResponse(savedApplication);
    }

    // GET ALL
    public List<JobApplicationResponse> getAllApplications() {

        List<JobApplication> applications = jobApplicationRepo.findAll();

        return applications.stream()
                .map(this::mapToResponse)
                .toList();
    }

    // GET BY ID
    public JobApplicationResponse getApplicationById(Long id) {

        JobApplication application = jobApplicationRepo.findById(id)
                .orElseThrow(() ->
                        new ApplicationNotFound(
                                "Application not found with id: " + id
                        )
                );

        return mapToResponse(application);
    }

    // UPDATE
    public JobApplicationResponse updateApplication(
            Long id,
            JobApplicationRequest request) {

        JobApplication existingApplication = jobApplicationRepo.findById(id)
                .orElseThrow(() ->
                        new ApplicationNotFound(
                                "Application not found with id: " + id
                        )
                );

        existingApplication.setCompany(request.getCompany());
        existingApplication.setRole(request.getRole());
        existingApplication.setJobUrl(request.getJobUrl());
        existingApplication.setAppliedDate(request.getAppliedDate());
        existingApplication.setResumeUrl(request.getResumeUrl());
        existingApplication.setDeadline(request.getDeadline());
        existingApplication.setNotes(request.getNotes());

        JobApplication savedApplication =
                jobApplicationRepo.save(existingApplication);

        return mapToResponse(savedApplication);
    }

    // DELETE
    public void deleteApplication(Long id) {

        if (!jobApplicationRepo.existsById(id)) {
            throw new ApplicationNotFound(
                    "Application not found with id: " + id
            );
        }

        jobApplicationRepo.deleteById(id);
    }

    // UPDATE STATUS
    public JobApplicationResponse updateStatus(
            Long id,
            ApplicationStatus newStatus) {

        JobApplication application = jobApplicationRepo.findById(id)
                .orElseThrow(() ->
                        new ApplicationNotFound(
                                "Application not found with id: " + id
                        )
                );

        ApplicationStatus currentStatus = application.getStatus();

        if (!isValidTransition(currentStatus, newStatus)) {
            throw new RuntimeException("Invalid status transition");
        }

        application.setStatus(newStatus);

        if (newStatus == ApplicationStatus.APPLIED) {
            application.setAppliedDate(LocalDate.now());
        }

        JobApplication savedApplication =
                jobApplicationRepo.save(application);

        return mapToResponse(savedApplication);
    }

    // ENTITY → RESPONSE DTO
    private JobApplicationResponse mapToResponse(
            JobApplication application) {

        JobApplicationResponse response =
                new JobApplicationResponse();

        response.setId(application.getId());
        response.setCompany(application.getCompany());
        response.setRole(application.getRole());
        response.setJobUrl(application.getJobUrl());
        response.setStatus(application.getStatus());
        response.setAppliedDate(application.getAppliedDate());
        response.setDeadline(application.getDeadline());
        response.setResumeUrl(application.getResumeUrl());
        response.setNotes(application.getNotes());
        response.setCreatedAt(application.getCreatedAt());
        response.setUpdatedAt(application.getUpdatedAt());

        return response;
    }

    // STATUS TRANSITION VALIDATION
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

        if (currentStatus == ApplicationStatus.SCREENING &&
                newStatus == ApplicationStatus.INTERVIEW) {
            return true;
        }

        if (currentStatus == ApplicationStatus.INTERVIEW &&
                newStatus == ApplicationStatus.OFFER) {
            return true;
        }

        if (currentStatus == ApplicationStatus.APPLIED &&
                newStatus == ApplicationStatus.REJECTED) {
            return true;
        }

        if (currentStatus == ApplicationStatus.SCREENING &&
                newStatus == ApplicationStatus.REJECTED) {
            return true;
        }

        if (currentStatus == ApplicationStatus.SAVED &&
                newStatus == ApplicationStatus.WITHDRAWN) {
            return true;
        }

        if (currentStatus == ApplicationStatus.APPLIED &&
                newStatus == ApplicationStatus.WITHDRAWN) {
            return true;
        }

        if (currentStatus == ApplicationStatus.SCREENING &&
                newStatus == ApplicationStatus.WITHDRAWN) {
            return true;
        }

        if (currentStatus == ApplicationStatus.INTERVIEW &&
                newStatus == ApplicationStatus.WITHDRAWN) {
            return true;
        }

        return false;
    }
    public List<JobApplicationResponse> searchApplications(String keyword) {

        List<JobApplication> applications =
                jobApplicationRepo
                        .findByCompanyContainingIgnoreCaseOrRoleContainingIgnoreCase(
                                keyword,
                                keyword
                        );

        return applications.stream()
                .map(this::mapToResponse)
                .toList();
    }
    public List<JobApplicationResponse> filterByStatus(
            ApplicationStatus status) {

        List<JobApplication> applications =
                jobApplicationRepo.findByStatus(status);

        return applications.stream()
                .map(this::mapToResponse)
                .toList();
    }
    public Page<JobApplicationResponse> getApplications(
            Pageable pageable) {

        return jobApplicationRepo.findAll(pageable)
                .map(this::mapToResponse);
    }
    public DashboardStatisticsResponse getDashboardStatistics() {

        DashboardStatisticsResponse response =
                new DashboardStatisticsResponse();

        response.setTotalApplications(
                jobApplicationRepo.count()
        );

        response.setSaved(
                jobApplicationRepo.countByStatus(ApplicationStatus.SAVED)
        );

        response.setApplied(
                jobApplicationRepo.countByStatus(ApplicationStatus.APPLIED)
        );

        response.setScreening(
                jobApplicationRepo.countByStatus(ApplicationStatus.SCREENING)
        );

        response.setInterview(
                jobApplicationRepo.countByStatus(ApplicationStatus.INTERVIEW)
        );

        response.setOffer(
                jobApplicationRepo.countByStatus(ApplicationStatus.OFFER)
        );

        response.setRejected(
                jobApplicationRepo.countByStatus(ApplicationStatus.REJECTED)
        );

        response.setWithdrawn(
                jobApplicationRepo.countByStatus(ApplicationStatus.WITHDRAWN)
        );

        return response;
    }
    public List<JobApplicationResponse> filterByDeadline(
            LocalDate from,
            LocalDate to) {

        List<JobApplication> applications =
                jobApplicationRepo.findByDeadlineBetween(from, to);

        return applications.stream()
                .map(this::mapToResponse)
                .toList();
    }
}