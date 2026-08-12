package com.ninay.JobTrakrr.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class InterviewRound {
    @Id
    @GeneratedValue
    private Long id;

    private String roundName;

    private LocalDate date;

    private String outcome;

    private String notes;

    @ManyToOne
    @JoinColumn(name =   "job_application_id")
    private JobApplication jobApplication;
}
