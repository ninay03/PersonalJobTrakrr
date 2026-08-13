package com.ninay.JobTrakrr.repository;

import com.ninay.JobTrakrr.model.ApplicationStatus;
import com.ninay.JobTrakrr.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface JobApplicationRepo
        extends JpaRepository<JobApplication, Long> {

    List<JobApplication> findByCompanyContainingIgnoreCaseOrRoleContainingIgnoreCase(
            String company,
            String role
    );

    List<JobApplication> findByStatus(ApplicationStatus status);

    long countByStatus(ApplicationStatus status);

    List<JobApplication> findByDeadlineBetween(
            LocalDate from,
            LocalDate to
    );
}