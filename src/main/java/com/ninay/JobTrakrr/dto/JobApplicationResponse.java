package com.ninay.JobTrakrr.dto;

import com.ninay.JobTrakrr.model.ApplicationStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class JobApplicationResponse {
    private Long id;
    private String company;
    private String role;
    private String jobUrl;
    private ApplicationStatus status;
    private LocalDate appliedDate;
    private LocalDate deadline;
    private String resumeUrl;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}