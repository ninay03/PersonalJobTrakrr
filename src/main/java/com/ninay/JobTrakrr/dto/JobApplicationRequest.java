package com.ninay.JobTrakrr.dto;

import com.ninay.JobTrakrr.model.ApplicationStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class JobApplicationRequest {

    @NotBlank(message = "Company is required")
    private String company;

    @NotBlank(message = "Role is required")
    private String role;

    @Pattern(
            regexp = "^(https?://).+",
            message = "Job URL must start with http:// or https://"
    )
    private String jobUrl;

    private ApplicationStatus status;

    @PastOrPresent(message = "Applied date cannot be in the future")
    private LocalDate appliedDate;

    @FutureOrPresent(message = "Deadline cannot be in the past")
    private LocalDate deadline;

    @Pattern(
            regexp = "^(https?://).+",
            message = "Resume URL must start with http:// or https://"
    )
    private String resumeUrl;

    private String notes;
}