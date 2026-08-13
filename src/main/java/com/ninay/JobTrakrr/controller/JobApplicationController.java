package com.ninay.JobTrakrr.controller;

import com.ninay.JobTrakrr.dto.DashboardStatisticsResponse;
import com.ninay.JobTrakrr.dto.JobApplicationRequest;
import com.ninay.JobTrakrr.dto.JobApplicationResponse;
import com.ninay.JobTrakrr.model.ApplicationStatus;
import com.ninay.JobTrakrr.service.JobApplicationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    public JobApplicationController(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @PostMapping("/application")
    @ResponseStatus(HttpStatus.CREATED)
    public JobApplicationResponse createApplication(
            @RequestBody @Valid JobApplicationRequest request) {

        return jobApplicationService.createApplication(request);
    }

    @GetMapping("/application")
    public List<JobApplicationResponse> getAllApplication() {
        return jobApplicationService.getAllApplications();
    }

    @GetMapping("/application/{id}")
    public JobApplicationResponse getApplicationById(
            @PathVariable Long id) {

        return jobApplicationService.getApplicationById(id);
    }

    @PutMapping("/application/{id}")
    public JobApplicationResponse updateApplication(
            @PathVariable Long id,
            @RequestBody @Valid JobApplicationRequest request) {

        return jobApplicationService.updateApplication(id, request);
    }

    @DeleteMapping("/application/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteApplication(@PathVariable Long id) {
        jobApplicationService.deleteApplication(id);
    }

    @PatchMapping("/application/{id}/status")
    public JobApplicationResponse updateStatus(
            @PathVariable Long id,
            @RequestBody ApplicationStatus newStatus) {

        return jobApplicationService.updateStatus(id, newStatus);
    }
    @GetMapping("/application/search")
    public List<JobApplicationResponse> searchApplications(
            @RequestParam String keyword) {

        return jobApplicationService.searchApplications(keyword);
    }
    @GetMapping("/application/filter")
    public List<JobApplicationResponse> filterByStatus(
            @RequestParam ApplicationStatus status) {

        return jobApplicationService.filterByStatus(status);
    }
    @GetMapping("/application/page")
    public Page<JobApplicationResponse> getApplications(
            Pageable pageable) {

        return jobApplicationService.getApplications(pageable);
    }
    @GetMapping("/application/statistics")
    public DashboardStatisticsResponse getDashboardStatistics() {

        return jobApplicationService.getDashboardStatistics();
    }
    @GetMapping("/application/deadline")
    public List<JobApplicationResponse> filterByDeadline(
            @RequestParam LocalDate from,
            @RequestParam LocalDate to) {

        return jobApplicationService.filterByDeadline(from, to);
    }


}