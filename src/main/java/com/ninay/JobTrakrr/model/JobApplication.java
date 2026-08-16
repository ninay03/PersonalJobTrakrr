package com.ninay.JobTrakrr.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter

public class JobApplication {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String company;

    private String role;

    private String jobUrl;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    private LocalDate appliedDate;

    private LocalDate deadline;

    private String resumeUrl;

    private String notes;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "jobApplication")
    private List<InterviewRound> interviewRounds;
}
