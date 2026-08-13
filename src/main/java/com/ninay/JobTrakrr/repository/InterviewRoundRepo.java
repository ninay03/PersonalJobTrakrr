package com.ninay.JobTrakrr.repository;

import com.ninay.JobTrakrr.model.InterviewRound;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewRoundRepo
        extends JpaRepository<InterviewRound, Long> {

    List<InterviewRound> findByJobApplicationId(Long jobApplicationId);
}