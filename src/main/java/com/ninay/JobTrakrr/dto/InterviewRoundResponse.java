package com.ninay.JobTrakrr.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class InterviewRoundResponse {

    private Long id;

    private String roundName;

    private LocalDate date;

    private String outcome;

    private String notes;

    private Long jobApplicationId;
}