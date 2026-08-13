package com.ninay.JobTrakrr.service;

import com.ninay.JobTrakrr.dto.InterviewRoundRequest;
import com.ninay.JobTrakrr.dto.InterviewRoundResponse;
import com.ninay.JobTrakrr.exception.ApplicationNotFound;
import com.ninay.JobTrakrr.exception.InterviewRoundNotFound;
import com.ninay.JobTrakrr.model.InterviewRound;
import com.ninay.JobTrakrr.model.JobApplication;
import com.ninay.JobTrakrr.repository.InterviewRoundRepo;
import com.ninay.JobTrakrr.repository.JobApplicationRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterviewRoundService {

    private final InterviewRoundRepo interviewRoundRepo;
    private final JobApplicationRepo jobApplicationRepo;

    public InterviewRoundService(
            InterviewRoundRepo interviewRoundRepo,
            JobApplicationRepo jobApplicationRepo) {

        this.interviewRoundRepo = interviewRoundRepo;
        this.jobApplicationRepo = jobApplicationRepo;
    }

    // CREATE INTERVIEW ROUND
    public InterviewRoundResponse createInterviewRound(
            Long applicationId,
            InterviewRoundRequest request) {

        JobApplication application = jobApplicationRepo.findById(applicationId)
                .orElseThrow(() ->
                        new ApplicationNotFound(
                                "Application not found with id: " + applicationId
                        )
                );

        InterviewRound interviewRound = new InterviewRound();

        interviewRound.setRoundName(request.getRoundName());
        interviewRound.setDate(request.getDate());
        interviewRound.setOutcome(request.getOutcome());
        interviewRound.setNotes(request.getNotes());
        interviewRound.setJobApplication(application);

        InterviewRound savedRound =
                interviewRoundRepo.save(interviewRound);

        return mapToResponse(savedRound);
    }

    // GET ALL INTERVIEW ROUNDS
    public List<InterviewRoundResponse> getInterviewRounds(
            Long applicationId) {

        if (!jobApplicationRepo.existsById(applicationId)) {
            throw new ApplicationNotFound(
                    "Application not found with id: " + applicationId
            );
        }

        return interviewRoundRepo
                .findByJobApplicationId(applicationId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // GET INTERVIEW ROUND BY ID
    public InterviewRoundResponse getInterviewRoundById(Long id) {

        InterviewRound interviewRound = interviewRoundRepo.findById(id)
                .orElseThrow(() ->
                        new InterviewRoundNotFound(
                                "Interview round not found with id: " + id
                        )
                );

        return mapToResponse(interviewRound);
    }

    // UPDATE INTERVIEW ROUND
    public InterviewRoundResponse updateInterviewRound(
            Long id,
            InterviewRoundRequest request) {

        InterviewRound existingRound = interviewRoundRepo.findById(id)
                .orElseThrow(() ->
                        new InterviewRoundNotFound(
                                "Interview round not found with id: " + id
                        )
                );

        existingRound.setRoundName(request.getRoundName());
        existingRound.setDate(request.getDate());
        existingRound.setOutcome(request.getOutcome());
        existingRound.setNotes(request.getNotes());

        InterviewRound savedRound =
                interviewRoundRepo.save(existingRound);

        return mapToResponse(savedRound);
    }

    // DELETE INTERVIEW ROUND
    public void deleteInterviewRound(Long id) {

        if (!interviewRoundRepo.existsById(id)) {
            throw new InterviewRoundNotFound(
                    "Interview round not found with id: " + id
            );
        }

        interviewRoundRepo.deleteById(id);
    }

    // ENTITY → RESPONSE DTO
    private InterviewRoundResponse mapToResponse(
            InterviewRound interviewRound) {

        InterviewRoundResponse response =
                new InterviewRoundResponse();

        response.setId(interviewRound.getId());
        response.setRoundName(interviewRound.getRoundName());
        response.setDate(interviewRound.getDate());
        response.setOutcome(interviewRound.getOutcome());
        response.setNotes(interviewRound.getNotes());
        response.setJobApplicationId(
                interviewRound.getJobApplication().getId()
        );

        return response;
    }
}