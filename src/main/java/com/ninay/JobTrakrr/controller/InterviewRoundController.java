package com.ninay.JobTrakrr.controller;

import com.ninay.JobTrakrr.dto.InterviewRoundRequest;
import com.ninay.JobTrakrr.dto.InterviewRoundResponse;
import com.ninay.JobTrakrr.service.InterviewRoundService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InterviewRoundController {

    private final InterviewRoundService interviewRoundService;

    public InterviewRoundController(
            InterviewRoundService interviewRoundService) {

        this.interviewRoundService = interviewRoundService;
    }

    // CREATE INTERVIEW ROUND
    @PostMapping("/application/{applicationId}/interview-round")
    public InterviewRoundResponse createInterviewRound(
            @PathVariable Long applicationId,
            @RequestBody @Valid InterviewRoundRequest request) {

        return interviewRoundService.createInterviewRound(
                applicationId,
                request
        );
    }

    // GET ALL INTERVIEW ROUNDS
    @GetMapping("/application/{applicationId}/interview-round")
    public List<InterviewRoundResponse> getInterviewRounds(
            @PathVariable Long applicationId) {

        return interviewRoundService.getInterviewRounds(applicationId);
    }
    @GetMapping("/interview-round/{id}")
    public InterviewRoundResponse getInterviewRoundById(
            @PathVariable Long id) {

        return interviewRoundService.getInterviewRoundById(id);
    }
    @PutMapping("/interview-round/{id}")
    public InterviewRoundResponse updateInterviewRound(
            @PathVariable Long id,
            @RequestBody @Valid InterviewRoundRequest request) {

        return interviewRoundService.updateInterviewRound(id, request);
    }
    @DeleteMapping("/interview-round/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInterviewRound(@PathVariable Long id) {
        interviewRoundService.deleteInterviewRound(id);
    }
}