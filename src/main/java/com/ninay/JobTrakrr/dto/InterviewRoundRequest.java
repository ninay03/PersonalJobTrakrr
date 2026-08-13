package com.ninay.JobTrakrr.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class InterviewRoundRequest {

    @NotBlank(message = "Round name is required")
    private String roundName;

    @FutureOrPresent(message = "Interview date cannot be in the past")
    private LocalDate date;

    private String outcome;

    private String notes;
}