package com.ninay.JobTrakrr.repository;

import com.ninay.JobTrakrr.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobApplicationRepo extends JpaRepository<JobApplication, Long> {
}
